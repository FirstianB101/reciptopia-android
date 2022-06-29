package com.ich.reciptopia.domain.use_case.analyze_ingredient

import android.graphics.Bitmap
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.repository.AnalyzeIngredientFakeRepository
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ImageAnalyzeUseCaseTest {

    lateinit var useCase: ImageAnalyzeUseCase
    lateinit var fakeRepository: AnalyzeIngredientFakeRepository

    @Before
    fun setUp() {
        fakeRepository = AnalyzeIngredientFakeRepository()
        useCase = ImageAnalyzeUseCase(fakeRepository)
    }

    @Test
    fun `send images with no errors`() = runBlocking{
        val bitmaps = List(5){mockk<Bitmap>()}
        fakeRepository.exception = null

        useCase(bitmaps).collectIndexed { index, value ->
            when(index){
                0 -> assertEquals(value.javaClass, Resource.Loading::class.java)
                1 -> {
                    assertEquals(value.javaClass, Resource.Success::class.java)
                    val result = value.data?.response_data?.predicts
                    result?.keys?.forEach {
                        assertEquals(result[it], "ingredient${it}")
                    }
                }
            }
        }
    }

    @Test
    fun `send images with http exception`() = runBlocking{
        val bitmaps = List(5){mockk<Bitmap>()}
        fakeRepository.exception = AnalyzeIngredientFakeRepository.ExceptionCase.HttpException

        useCase(bitmaps).collectIndexed { index, value ->
            when(index){
                0 -> assertEquals(value.javaClass, Resource.Loading::class.java)
                1 -> {
                    assertEquals(value.javaClass, Resource.Error::class.java)
                }
            }
        }
    }

    @Test
    fun `send images with io exception`() = runBlocking{
        val bitmaps = List(5){mockk<Bitmap>()}
        fakeRepository.exception = AnalyzeIngredientFakeRepository.ExceptionCase.IOException

        useCase(bitmaps).collectIndexed { index, value ->
            when(index){
                0 -> assertEquals(value.javaClass, Resource.Loading::class.java)
                1 -> {
                    assertEquals(value.javaClass, Resource.Error::class.java)
                }
            }
        }
    }
}