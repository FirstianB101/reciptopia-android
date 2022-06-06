package com.ich.reciptopia.presentation.my_page.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.use_case.my_page.profile.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: ProfileUseCases,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observeUserChanged()
    }

    fun onEvent(event: ProfileScreenEvent){
        when(event){
            is ProfileScreenEvent.EditProfile -> viewModelScope.launch{
                editNickname(event.nickname)
                if(event.profileImg != null){
                    uploadProfileImg(event.profileImg).join()
                    getAccountProfileImage()
                    app.editAccount(_state.value.curAccount!!)
                }
            }
            is ProfileScreenEvent.EditDialogStateChanged -> {
                _state.value = _state.value.copy(
                    showEditDialogState = event.show
                )
            }
        }
    }

    private fun observeUserChanged(){
        viewModelScope.launch {
            app.user.collect { user ->
                _state.value = _state.value.copy(
                    curAccount = user?.account
                )
                if(user != null) {
                    getAccountProfileImage()
                }
            }
        }
    }

    private fun editNickname(newNickname: String) = viewModelScope.launch {
        val edited = _state.value.curAccount?.copy(
            nickname = newNickname
        )!!
        useCases.editNickname(edited).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curAccount = result.data!!,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("정보가 수정되었습니다"))
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

    private fun uploadProfileImg(image: Bitmap) = viewModelScope.launch {
        val ownerId = _state.value.curAccount?.id
        if (ownerId != null) {
            useCases.uploadProfileImg(ownerId, image).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
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
                        _eventFlow.emit(UiEvent.ShowToast("이미지 업로드에 실패했습니다 (${result.message})"))
                    }
                }
            }
        }
    }

    private fun getAccountProfileImage() = viewModelScope.launch {
        val ownerId = _state.value.curAccount?.id
        if (ownerId != null) {
            useCases.getAccountProfileImg(ownerId).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            profileImage = result.data,
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
                        _eventFlow.emit(UiEvent.ShowToast("이미지를 불러오지 못했습니다 (${result.message})"))
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
        object CloseEditDialog: UiEvent()
    }
}