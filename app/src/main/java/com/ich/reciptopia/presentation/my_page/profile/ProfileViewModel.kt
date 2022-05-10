package com.ich.reciptopia.presentation.my_page.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.use_case.my_page.profile.NicknameChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: NicknameChangeUseCase,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState(
        nickname = app.getCurrentUser()?.account?.nickname ?: "",
        email = app.getCurrentUser()?.account?.email ?: ""
    ))
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ProfileScreenEvent){
        when(event){
            is ProfileScreenEvent.ChangeNickname -> {
                changeNickname(event.nickname)
            }
            is ProfileScreenEvent.EditDialogStateChanged -> {
                _state.value = _state.value.copy(
                    showEditDialogState = event.show
                )
            }
        }
    }

    private fun changeNickname(newNickname: String) = viewModelScope.launch {
        val edited = app.getCurrentUser()?.account?.also { it.nickname = newNickname }!!
        useCase(edited).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        nickname = result.data?.nickname!!,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("닉네임이 수정되었습니다"))
                    _eventFlow.emit(UiEvent.CloseEditDialog)
                    app.editAccount(result.data)
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
                    _eventFlow.emit(UiEvent.ShowToast("닉네임을 수정하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
        object CloseEditDialog: UiEvent()
    }
}