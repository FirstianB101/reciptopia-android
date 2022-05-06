package com.ich.reciptopia.presentation.community

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()

    fun onEvent(event: CommunityScreenEvent){
        when(event){
            is CommunityScreenEvent.CreateBoardStateChanged -> {
                _state.value = _state.value.copy(
                    showCreateBoardDialog = event.isOn
                )
            }
            is CommunityScreenEvent.SearchButtonClicked -> {
                val searchModeIsOn = _state.value.searchMode
                if(searchModeIsOn){
                    // 검색 시작
                }else{
                    _state.value = _state.value.copy(
                        searchMode = true
                    )
                }
            }
            is CommunityScreenEvent.SearchModeOff -> {
                _state.value = _state.value.copy(
                    searchMode = false
                )
            }
            is CommunityScreenEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
        }
    }
}