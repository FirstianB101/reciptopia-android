package com.ich.reciptopia.domain.use_case.search_history

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetSearchHistoriesInDBTest{

    private lateinit var getSearchHistoriesInDB: GetSearchHistoriesInDB
    private lateinit var fakeRepository: FakeSearchHistoryRepository
    private val testHistories = mutableListOf<SearchHistory>()

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        getSearchHistoriesInDB = GetSearchHistoriesInDB(fakeRepository)
    }

    @Test
    fun `get search history test`() = runBlocking {
        var data = fakeRepository.getSearchHistories().first()
        assert(data.isEmpty())

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

        data = fakeRepository.getSearchHistories().first()
        assert(data.size == testHistories.size)

        for(i in data.indices){
            assert(data[i].ingredients[0].text == "ingredient${i+1}")
            assert(data[i].ingredients[1].text == "ingredient${i+2}")
        }
    }
}