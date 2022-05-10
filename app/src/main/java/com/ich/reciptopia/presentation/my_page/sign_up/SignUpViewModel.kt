package com.ich.reciptopia.presentation.my_page.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.use_case.my_page.sign_up.SignUpUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: SignUpUseCases
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
            is SignUpScreenEvent.SignUp -> {
                val infoFilled = allInfoFilled()
                if(infoFilled is SignUpFormatErrors.NoError){
                    checkEmailExists().invokeOnCompletion {
                        if(!_state.value.emailExist)
                            signUp()
                    }
                }else{
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowSnackbar(infoFilled.msg))
                    }
                }
            }
        }
    }

    private fun checkEmailExists() = viewModelScope.launch {
        useCases.emailExists(_state.value.email).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        emailExist = result.data!!,
                        isLoading = false
                    )
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
                    _eventFlow.emit(UiEvent.ShowToast("이메일 중복 확인에 실패했습니다(${result.message})"))
                }
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        val newAccount = Account(
            email = _state.value.email,
            password = _state.value.password,
            nickname = _state.value.nickname
        )
        useCases.createAccount(newAccount).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.SignUpSuccess)
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
                    _eventFlow.emit(UiEvent.ShowToast("회원가입에 실패했습니다. (${result.message})"))
                }
            }
        }
    }

    private fun allInfoFilled(): SignUpFormatErrors{
        return when{
            _state.value.email.isBlank() -> SignUpFormatErrors.EmailBlank
            !_state.value.email.contains('@') -> SignUpFormatErrors.EmailFormatError
            _state.value.password.isBlank() -> SignUpFormatErrors.PasswordBlank
            _state.value.password != _state.value.passwordCheck -> SignUpFormatErrors.PasswordCheckError
            _state.value.nickname.isBlank() -> SignUpFormatErrors.NicknameBlank
            _state.value.password.length < Constants.PW_MIN_LENGTH -> SignUpFormatErrors.PasswordTooShort
            _state.value.password.length > Constants.PW_MAX_LENGTH -> SignUpFormatErrors.PasswordTooLong
            !Pattern.matches(Constants.PW_EXPRESSION, _state.value.password) -> SignUpFormatErrors.PasswordFormatError
            else -> SignUpFormatErrors.NoError
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
        data class ShowSnackbar(val message: String): UiEvent()
        object SignUpSuccess: UiEvent()
    }
}