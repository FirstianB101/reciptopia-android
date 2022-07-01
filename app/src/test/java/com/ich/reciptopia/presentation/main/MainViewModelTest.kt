package com.ich.reciptopia.presentation.main

import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.application.ReciptopiaApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(ReciptopiaApplication())
    }

    @Test
    fun `검색 버튼 클릭에 따른 상태 변화 테스트`() {
        assertThat(viewModel.state.value.searchMode).isFalse()
        // search mode on
        viewModel.onEvent(MainScreenEvent.SearchModeChanged(true))
        assertThat(viewModel.state.value.searchMode).isTrue()
        // search mode off
        viewModel.onEvent(MainScreenEvent.SearchModeChanged(false))
        assertThat(viewModel.state.value.searchMode).isFalse()
        // search mode on
        viewModel.onEvent(MainScreenEvent.SearchModeChanged(true))
        assertThat(viewModel.state.value.searchMode).isTrue()
    }

    @Test
    fun `칩 추가 및 제거 테스트`() {
        for (i in 1..5) {
            viewModel.onEvent(MainScreenEvent.SearchQueryChanged("test$i"))

            viewModel.onEvent(MainScreenEvent.AddChip)

            val chips = viewModel.state.value.chipStates
            assertThat(chips.size).isEqualTo(i)
            assertThat(chips[i - 1].text).isEqualTo("test$i")
        }

        val chips = viewModel.state.value.chipStates
        for (i in 0..4) {
            viewModel.onEvent(MainScreenEvent.RemoveChip(chips[i]))

            val curChips = viewModel.state.value.chipStates
            assertThat(curChips.size).isEqualTo(4 - i)
            if (curChips.isNotEmpty()) {
                assertThat(curChips[0].text).isEqualTo("test${i + 2}")
            }
        }
    }

    @Test
    fun `칩 리셋 테스트`() {
        for (i in 1..5) {
            viewModel.onEvent(MainScreenEvent.SearchQueryChanged("test$i"))
            viewModel.onEvent(MainScreenEvent.AddChip)
        }
        assertThat(viewModel.state.value.chipStates.size).isEqualTo(5)

        viewModel.onEvent(MainScreenEvent.ResetChips)

        assertThat(viewModel.state.value.chipStates.isEmpty()).isTrue()
    }

    @Test
    fun `칩 클릭에 의한 메인 서브 재료 지정 테스트`(){
        for (i in 0..4) {
            viewModel.onEvent(MainScreenEvent.SearchQueryChanged("test$i"))
            viewModel.onEvent(MainScreenEvent.AddChip)
        }
        viewModel.state.value.chipStates.forEach {
            assertThat(it.isSubIngredient.value).isTrue()
        }

        // 1번과 3번 칩 클릭
        viewModel.onEvent(MainScreenEvent.OnChipClicked(1))
        viewModel.onEvent(MainScreenEvent.OnChipClicked(3))

        viewModel.state.value.chipStates.forEachIndexed { idx, chipState ->
            if(idx % 2 == 0){
                assertThat(chipState.isSubIngredient.value).isTrue()
            }else{
                assertThat(chipState.isSubIngredient.value).isFalse()
            }
        }
    }
}