package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import org.junit.Before

class DoSearchUseCaseEntityEntityInDBTest {

    private lateinit var addSearchHistoryInDBUseCase: AddSearchHistoryInDBUseCase
    private lateinit var fakeRepository: FakeSearchHistoryRepository

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        addSearchHistoryInDBUseCase = AddSearchHistoryInDBUseCase(fakeRepository)
    }

//    @Test
//    fun `add search history test`() = runBlocking {
//        val testHistories = mutableListOf<SearchHistory>()
//        for(i in 1..5){
//            testHistories.add(
//                SearchHistory(
//                    listOf(
//                        ChipInfo("ingredient$i",false),
//                        ChipInfo("ingredient${i+1}",true)
//                    )
//                )
//            )
//        }
//        testHistories.forEach { addSearchHistoryInDB(it) }
//
//        val data = fakeRepository.getSearchHistoryEntities().first()
//
//        for(i in data.indices){
//            assert("ingredient${i+1}" == data[i].ingredients[0].text)
//            assert(!data[i].ingredients[0].isSubIngredient)
//            assert("ingredient${i+2}" == data[i].ingredients[1].text)
//            assert(data[i].ingredients[1].isSubIngredient)
//        }
//    }
}