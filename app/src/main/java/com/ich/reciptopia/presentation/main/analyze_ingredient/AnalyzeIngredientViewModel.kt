package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import com.ich.reciptopia.domain.use_case.analyze_ingredient.ImageAnalyzeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzeIngredientViewModel @Inject constructor(
    private val analyzeImage: ImageAnalyzeUseCase
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
            is AnalyzeIngredientEvent.CloseAnalyzeResultDialog -> {
                _state.value = _state.value.copy(
                    showAnalyzeResultDialog = false
                )
            }
            is AnalyzeIngredientEvent.StartAnalyzing -> {
                val images = _state.value.images
                analyzeImages(images)
            }
        }
    }

    private fun analyzeImages(images: List<Bitmap>) = viewModelScope.launch {
        analyzeImage(images).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val data = result.data!!
                    _state.value = _state.value.copy(
                        analyzeResults = data.response_data?.predicts!!,
                        showAnalyzeResultDialog = true,
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
                    _eventFlow.emit(UiEvent.ShowToast("이미지 분석에 실패했습니다 (${result.message})"))
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}