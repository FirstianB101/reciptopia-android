package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSearchHistoryEntitiesTest{

    private lateinit var getSearchHistoryEntities: GetSearchHistoryEntities
    private lateinit var fakeRepository: FakeSearchHistoryRepository
    private val testHistories = mutableListOf<SearchHistoryEntity>()

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        getSearchHistoryEntities = GetSearchHistoryEntities(fakeRepository)
    }

    @Test
    fun `get search history test`() = runBlocking {
        var data = getSearchHistoryEntities().first()
        assert(data.isEmpty())

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

        data = getSearchHistoryEntities().first()
        assert(data.size == testHistories.size)

        for(i in data.indices){
            assert(data[i].ingredients[0].text == "ingredient${i+1}")
            assert(data[i].ingredients[1].text == "ingredient${i+2}")
        }
    }
}