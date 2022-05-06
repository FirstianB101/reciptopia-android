package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class DeleteSearchHistoryEntityTest {

    private lateinit var deleteSearchHistory: DeleteSearchHistory
    private lateinit var fakeRepository: FakeSearchHistoryRepository
    private val testHistories = mutableListOf<SearchHistoryEntity>()

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        deleteSearchHistory = DeleteSearchHistory(fakeRepository)

        for(i in 1..5){
            testHistories.add(
                SearchHistoryEntity(
                    listOf(
                        ChipInfo("ingredient$i",false),
                        ChipInfo("ingredient${i+1}",true)
                    )
                )
            )
        }
        runBlocking {
            testHistories.forEach { fakeRepository.insertSearchHistory(it) }
        }
    }

    @Test
    fun `delete search history test`() = runBlocking {
        // 1번과 3번 기록 삭제
        deleteSearchHistory(testHistories[1])
        deleteSearchHistory(testHistories[3])

        val data = fakeRepository.getSearchHistories().first()

        // 2개 삭제 되었는지 배열 크기 체크
        assert(data.size == testHistories.size - 2)

        for(i in data.indices){
            when(i){
                0 -> {
                    assert(data[i].ingredients[0].text == "ingredient1")
                    assert(data[i].ingredients[1].text == "ingredient2")
                }
                1 -> {
                    assert(data[i].ingredients[0].text == "ingredient3")
                    assert(data[i].ingredients[1].text == "ingredient4")
                }
                2 -> {
                    assert(data[i].ingredients[0].text == "ingredient5")
                    assert(data[i].ingredients[1].text == "ingredient6")
                }
            }
        }
    }
}