package com.ich.reciptopia.presentation.my_page.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.any

class LoginViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCase: LoginUseCase
    lateinit var viewModel: LoginViewModel
    lateinit var application: ReciptopiaApplication

    @Before
    fun setUp() {
        application = ReciptopiaApplication()
        useCase = LoginUseCase(LoginFakeRepository())
        viewModel = LoginViewModel(useCase, application)
    }

    @Test
    fun `이메일 입력 변경 테스트`(){
        assertThat(viewModel.state.value.email).isEqualTo("")
        viewModel.onEvent(LoginScreenEvent.EmailChanged("test"))
        assertThat(viewModel.state.value.email).isEqualTo("test")
        viewModel.onEvent(LoginScreenEvent.EmailChanged("test@gmail.com"))
        assertThat(viewModel.state.value.email).isEqualTo("test@gmail.com")
    }

    @Test
    fun `비밀번호 입력 변경 테스트`(){
        assertThat(viewModel.state.value.password).isEqualTo("")
        viewModel.onEvent(LoginScreenEvent.PasswordChanged("test"))
        assertThat(viewModel.state.value.password).isEqualTo("test")
        viewModel.onEvent(LoginScreenEvent.PasswordChanged("test1234"))
        assertThat(viewModel.state.value.password).isEqualTo("test1234")
    }

    @Test
    fun `로그인 테스트`() = runTest{
        viewModel.onEvent(LoginScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(LoginScreenEvent.PasswordChanged("test1234"))

        viewModel.onEvent(LoginScreenEvent.Login)

        viewModel.state.test {
            val initial = awaitItem() // skip initial state
            assertThat(initial.loginUser).isNull()

            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            val state = awaitItem()
            assertThat(state.loginUser?.token).isEqualTo("testtoken")
            assertThat(state.loginUser?.account?.nickname).isEqualTo("testnickname")
            assertThat(state.loginUser?.account?.email).isEqualTo("test@gmail.com")

            assertThat(application.user.value?.account?.nickname).isEqualTo("testnickname")
            assertThat(application.user.value?.account?.email).isEqualTo("test@gmail.com")
        }
    }

    @Test
    fun `로그아웃 테스트`() = runTest{
        viewModel.onEvent(LoginScreenEvent.EmailChanged("test@gmail.com"))
        viewModel.onEvent(LoginScreenEvent.PasswordChanged("test1234"))

        viewModel.onEvent(LoginScreenEvent.Login)

        viewModel.state.test {
            val initial = awaitItem() // skip initial state
            assertThat(initial.loginUser).isNull()

            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            val login = awaitItem()
            assertThat(application.user.value).isNotNull()
            assertThat(viewModel.state.value.loginUser).isNotNull()
        }

        viewModel.onEvent(LoginScreenEvent.Logout)
        assertThat(application.user.value).isNull()
    }
}