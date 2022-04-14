package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddSearchHistoryTest {

    private lateinit var addSearchHistory: AddSearchHistory
    private lateinit var fakeRepository: FakeSearchHistoryRepository

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        addSearchHistory = AddSearchHistory(fakeRepository)

        val testHistories = mutableListOf<SearchHistory>()
        for(i in 1..5){
            testHistories.add(
                SearchHistory(
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
    fun `add search history test`() = runBlocking {

    }
}