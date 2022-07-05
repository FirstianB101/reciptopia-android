package com.ich.reciptopia.presentation.my_page.sign_up

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.domain.repository.SignUpRepository
import com.ich.reciptopia.domain.use_case.my_page.sign_up.CreateAccountUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.EmailExistsUseCase
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCases: SignUpUseCases
    lateinit var viewModel: SignUpViewModel
    lateinit var repository: SignUpRepository
    lateinit var application: ReciptopiaApplication

    @Before
    fun setUp() {
        application = ReciptopiaApplication()
        repository = SignUpFakeRepository()
        useCases = SignUpUseCases(
            emailExists = EmailExistsUseCase(repository),
            createAccount = CreateAccountUseCase(repository)
        )
        viewModel = SignUpViewModel(useCases)
    }

    @Test
    fun `input 정보 입력 테스트`(){
        assertThat(viewModel.state.value.email).isEqualTo("")
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        assertThat(viewModel.state.value.email).isEqualTo("test@gmail.com")

        assertThat(viewModel.state.value.password).isEqualTo("")
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("1234"))
        assertThat(viewModel.state.value.password).isEqualTo("1234")

        assertThat(viewModel.state.value.passwordCheck).isEqualTo("")
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("1234"))
        assertThat(viewModel.state.value.passwordCheck).isEqualTo("1234")

        assertThat(viewModel.state.value.nickname).isEqualTo("")
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged("testnick"))
        assertThat(viewModel.state.value.nickname).isEqualTo("testnick")
    }

    @Test
    fun `회원가입 성공 테스트`() = runTest{
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("12345678"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("12345678"))
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged("testnick"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            assertThat(result).isEqualTo(SignUpViewModel.UiEvent.SignUpSuccess)
        }
    }

    @Test
    fun `이메일 입력 안하는 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged(""))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.EmailBlank.msg)
        }
    }

    @Test
    fun `이메일 형식 오류(@없음)의 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("testemail"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.EmailFormatError.msg)
        }
    }

    @Test
    fun `패스워드 입력 안하는 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged(""))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.PasswordBlank.msg)
        }
    }

    @Test
    fun `패스워드 체크 다를 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("12345678"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("87654321"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.PasswordCheckError.msg)
        }
    }

    @Test
    fun `닉네임 입력 안할 경우 메시지 출력 테스트`() = runTest{
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("12345678"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("12345678"))
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged(""))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.NicknameBlank.msg)
        }
    }

    @Test
    fun `패스워드 길이 짧은 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("1234"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("1234"))
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged("testnick"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.PasswordTooShort.msg)
        }
    }

    @Test
    fun `패스워드 길이 긴 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("12341234123412341"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("12341234123412341"))
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged("testnick"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.PasswordTooLong.msg)
        }
    }

    @Test
    fun `패스워드 사용할 수 없는 문자 포함된 경우 메시지 출력 테스트`() = runTest {
        viewModel.onEvent(SignUpScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(SignUpScreenEvent.PasswordChanged("꽑뛟123412"))
        viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged("꽑뛟123412"))
        viewModel.onEvent(SignUpScreenEvent.NicknameChanged("testnick"))

        viewModel.onEvent(SignUpScreenEvent.SignUp)
        viewModel.eventFlow.test {
            val result = awaitItem()
            val message = (result as SignUpViewModel.UiEvent.ShowSnackbar).message
            assertThat(message).isEqualTo(SignUpFormatErrors.PasswordFormatError.msg)
        }
    }
}