package com.ich.reciptopia.presentation.main.analyze_ingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzeIngredientViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(AnalyzeIngredientState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AnalyzeIngredientEvent){
        when(event){
            is AnalyzeIngredientEvent.OnImageCaptured -> {
                if(_state.value.images.size < Constants.MAX_IMAGE_CNT) {
                    _state.value = _state.value.copy(
                        images = _state.value.images.getAddedList(event.image)
                    )
                }
            }
            is AnalyzeIngredientEvent.DeleteImages -> {
                _state.value = _state.value.copy(
                    images = _state.value.images.getRemovedList(event.images)
                )
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowToast("선택한 사진을 제거했습니다."))
                }
            }
            is AnalyzeIngredientEvent.ManageDialogStateChanged -> {
                _state.value = _state.value.copy(
                    showManageDialog = event.show
                )
            }
            is AnalyzeIngredientEvent.ResultDialogStateChanged -> {
                _state.value = _state.value.copy(
                    showAnalyzeResultDialog = event.show
                )
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}