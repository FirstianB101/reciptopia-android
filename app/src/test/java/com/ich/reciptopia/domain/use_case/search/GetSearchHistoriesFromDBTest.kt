package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import org.junit.Before

class GetSearchHistoriesFromDBTest{

    private lateinit var getSearchHistoriesFromDB: GetSearchHistoriesFromDBUseCase
    private lateinit var fakeRepository: FakeSearchHistoryRepository
    private val testHistories = mutableListOf<SearchHistory>()

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        getSearchHistoriesFromDB = GetSearchHistoriesFromDBUseCase(fakeRepository)
    }

//    @Test
//    fun `get search history test`() = runBlocking {
//        var data = getSearchHistoriesFromDB().first()
//        assert(data.isEmpty())
//
//        for(i in 1..5){
//            testHistories.add(
//                SearchHistoryEntity(
//                    listOf(
//                        ChipInfo("ingredient$i",false),
//                        ChipInfo("ingredient${i+1}",true)
//                    )
//                )
//            )
//        }
//        runBlocking {
//            testHistories.forEach { fakeRepository.insertSearchHistory(it) }
//        }
//
//        data = getSearchHistoriesFromDB().first()
//        assert(data.size == testHistories.size)
//
//        for(i in data.indices){
//            assert(data[i].ingredients[0].text == "ingredient${i+1}")
//            assert(data[i].ingredients[1].text == "ingredient${i+2}")
//        }
//    }
}