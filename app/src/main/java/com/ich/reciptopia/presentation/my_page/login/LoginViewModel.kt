package com.ich.reciptopia.presentation.my_page.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.use_case.my_page.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginScreenEvent){
        when(event){
            is LoginScreenEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }
            is LoginScreenEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }
            is LoginScreenEvent.Login -> viewModelScope.launch(Dispatchers.IO){
                login().join()
                //getProfileImage()
            }
            is LoginScreenEvent.Logout -> {
                app.logout()
            }
        }
    }

    private fun login() = viewModelScope.launch{
        val auth = Auth(
            email = _state.value.email,
            password = _state.value.password
        )
        useCase(auth).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loginUser = result.data,
                        isLoading = false
                    )
                    app.login(result.data!!)
                    _eventFlow.emit(UiEvent.LoginSuccess)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("로그인에 실패했습니다 (${result.message})"))
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
        object LoginSuccess: UiEvent()
    }
}