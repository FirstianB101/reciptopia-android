package com.ich.reciptopia.presentation.my_page.profile

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.use_case.my_page.profile.EditNicknameUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.GetAccountProfileImgUseCase
import com.ich.reciptopia.domain.use_case.my_page.profile.ProfileUseCases
import com.ich.reciptopia.domain.use_case.my_page.profile.UploadProfileImgUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCases: ProfileUseCases
    lateinit var viewModel: ProfileViewModel
    lateinit var repository: ProfileFakeRepository
    lateinit var application: ReciptopiaApplication

    @Before
    fun setUp() {
        application = ReciptopiaApplication()
        repository = ProfileFakeRepository()
        useCases = ProfileUseCases(
            editNickname = EditNicknameUseCase(repository),
            uploadProfileImg = UploadProfileImgUseCase(repository),
            getAccountProfileImg = GetAccountProfileImgUseCase(repository)
        )
        viewModel = ProfileViewModel(useCases, application)
    }

    @Test
    fun `로그인 여부 변화 인식하고 currentUser 변화 여부 테스트`() = runTest {
        application.login(
            User(token = null, account = Account(
                id = 1,
                email = "test@gmail.com",
                nickname = "testnick"
            ))
        )
        // Fake Repository에서 Profile Image 가져오기 위한 유저
        repository.addAccount(Account(id = 1))

        viewModel.state.test {
            val initial = awaitItem()
            assertThat(initial.curAccount).isNull()

            val afterLogin = awaitItem()
            assertThat(afterLogin.curAccount?.id).isEqualTo(1)
            assertThat(afterLogin.curAccount?.email).isEqualTo("test@gmail.com")
            assertThat(afterLogin.curAccount?.nickname).isEqualTo("testnick")

            val loadingProfileImage = awaitItem()
            assertThat(loadingProfileImage.isLoading).isTrue()

            val afterLoadingProfileImage = awaitItem()
            assertThat(afterLoadingProfileImage.profileImage).isNotNull()

            application.logout()

            val afterLogout = awaitItem()
            assertThat(afterLogout.curAccount).isNull()
        }
    }

    @Test
    fun `닉네임 변경 테스트`() = runTest {
        // 기존 존재하는 계정이라고 가정
        val user = User(
            token = "test token",
            account = Account(
                id = 1,
                email = "test@gmail.com",
                nickname = "testnick"
            )
        )
        repository.addAccount(user.account!!)

        viewModel.state.test {
            val initial = awaitItem()
            assertThat(initial.curAccount).isNull()

            application.login(user)
            // 로그인 및 정보 가져오는 부분 스킵
            val afterLogin = awaitItem()
            val loadingProfileImage = awaitItem()
            val afterLoadingProfileImage = awaitItem()
            val fillCurAccountProfileImg = awaitItem()

            viewModel.onEvent(ProfileScreenEvent.EditProfile("newnick",null))

            val loadingEditNickname = awaitItem()
            assertThat(loadingEditNickname.isLoading).isTrue()

            val afterEditNickname = awaitItem()
            assertThat(afterEditNickname.curAccount?.nickname).isEqualTo("newnick")
        }
    }

    @Test
    fun `프로필 변경 테스트`() = runTest {
        // 기존 존재하는 계정이라고 가정
        val user = User(
            token = "test token",
            account = Account(
                id = 1,
                email = "test@gmail.com",
                nickname = "testnick"
            )
        )
        repository.addAccount(user.account!!)

        viewModel.state.test {
            val initial = awaitItem()
            assertThat(initial.curAccount).isNull()

            application.login(user)
            // 로그인 및 정보 가져오는 부분 스킵
            val afterLogin = awaitItem()
            val loadingProfileImage = awaitItem()
            val afterLoadingProfileImage = awaitItem()
            val fillCurAccountProfileImg = awaitItem()

            viewModel.onEvent(ProfileScreenEvent.EditProfile("newnick", mockk()))

            val loadingEditNickname = awaitItem()
            assertThat(loadingEditNickname.isLoading).isTrue()

            val afterEditNickname = awaitItem()
            assertThat(afterEditNickname.curAccount?.nickname).isEqualTo("newnick")

            val loadingUploadProfileImage = awaitItem()
            assertThat(loadingUploadProfileImage.isLoading).isTrue()

            val afterUploadImg = awaitItem()
            assertThat(afterUploadImg.isLoading).isFalse()

            val loadingReGetProfileImage = awaitItem()
            assertThat(loadingReGetProfileImage.isLoading).isTrue()

            val afterReGetProfileImage = awaitItem()
            assertThat(afterReGetProfileImage.profileImage).isNotNull()

            cancelAndConsumeRemainingEvents()
        }
    }
}