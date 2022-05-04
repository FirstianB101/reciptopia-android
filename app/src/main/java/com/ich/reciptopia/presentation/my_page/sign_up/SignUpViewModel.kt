package com.ich.reciptopia.presentation.my_page.sign_up

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): ViewModel(){

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignUpScreenEvent){
        when(event){
            is SignUpScreenEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }
            is SignUpScreenEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }
            is SignUpScreenEvent.PasswordCheckChanged -> {
                _state.value = _state.value.copy(
                    passwordCheck = event.passwordCheck
                )
            }
            is SignUpScreenEvent.NicknameChanged -> {
                _state.value = _state.value.copy(
                    nickname = event.nickname
                )
            }
            is SignUpScreenEvent.ShowNotification -> {
                _state.value = _state.value.copy(
                    showNotification = event.show
                )
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}