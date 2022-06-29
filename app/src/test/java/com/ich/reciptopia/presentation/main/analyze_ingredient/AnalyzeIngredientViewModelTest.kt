package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ich.reciptopia.MainDispatcherRule
import com.ich.reciptopia.domain.use_case.analyze_ingredient.ImageAnalyzeUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AnalyzeIngredientViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCase: ImageAnalyzeUseCase
    lateinit var viewModel: AnalyzeIngredientViewModel

    @Before
    fun setUp() {
        useCase = ImageAnalyzeUseCase(AnalyzeIngredientFakeRepository())
        viewModel = AnalyzeIngredientViewModel(useCase)
    }

    @Test
    fun `이미지 캡쳐 후 배열에 추가 여부 테스트`() {
        val testImages = List(5) { mockk<Bitmap>() }
        val previousSize = viewModel.state.value.images.size

        testImages.forEach {
            viewModel.onEvent(AnalyzeIngredientEvent.OnImageCaptured(it))
        }

        val afterSize = viewModel.state.value.images.size

        assert(previousSize + testImages.size == afterSize)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `이미지 삭제 후 배열에서 제거 여부 테스트`() = runTest {
        val testImages = List(5) { mockk<Bitmap>() }
        testImages.forEach {
            viewModel.onEvent(AnalyzeIngredientEvent.OnImageCaptured(it))
        }

        val previousSize = viewModel.state.value.images.size

        for (i in 4 downTo 0) {
            viewModel.onEvent(AnalyzeIngredientEvent.DeleteImage(i))
            val afterSize = viewModel.state.value.images.size
            assert(previousSize - (previousSize - i) == afterSize)
        }
    }

    @Test
    fun `사진 관리 다이얼로그 상태 변경 테스트`() {
        val previousShow = viewModel.state.value.showManageDialog

        viewModel.onEvent(AnalyzeIngredientEvent.ManageDialogStateChanged(true))

        var afterShow = viewModel.state.value.showManageDialog

        assert(previousShow == !afterShow)

        viewModel.onEvent(AnalyzeIngredientEvent.ManageDialogStateChanged(false))

        afterShow = viewModel.state.value.showManageDialog

        assert(previousShow == afterShow)
    }

    @Test
    fun `사진 분석 및 분석 결과 다이얼로그 상태 변경 테스트`() = runTest {
        withContext(Dispatchers.Default) {
            val testImages = List(5) { mockk<Bitmap>() }
            testImages.forEach {
                viewModel.onEvent(AnalyzeIngredientEvent.OnImageCaptured(it))
            }

            viewModel.onEvent(AnalyzeIngredientEvent.StartAnalyzing)

            viewModel.state.test {
                awaitItem() // skip initial state

                val loading = awaitItem()
                assertThat(loading.isLoading).isTrue()

                val state = awaitItem()
                for (key in state.analyzeResults.keys) {
                    assertThat(state.analyzeResults[key] == "ingredient$key")
                }

                assertThat(state.showAnalyzeResultDialog).isTrue()
            }
        }
    }
}