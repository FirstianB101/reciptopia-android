package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import com.ich.reciptopia.repository.FakeSearchHistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddSearchHistoryEntityEntityEntityTest {

    private lateinit var addSearchHistoryEntity: AddSearchHistoryEntity
    private lateinit var fakeRepository: FakeSearchHistoryRepository

    @Before
    fun setUp() {
        fakeRepository = FakeSearchHistoryRepository()
        addSearchHistoryEntity = AddSearchHistoryEntity(fakeRepository)
    }

    @Test
    fun `add search history test`() = runBlocking {
        val testHistories = mutableListOf<SearchHistoryEntity>()
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
        testHistories.forEach { addSearchHistoryEntity(it) }

        val data = fakeRepository.getSearchHistoryEntities().first()

        for(i in data.indices){
            assert("ingredient${i+1}" == data[i].ingredients[0].text)
            assert(!data[i].ingredients[0].isSubIngredient)
            assert("ingredient${i+2}" == data[i].ingredients[1].text)
            assert(data[i].ingredients[1].isSubIngredient)
        }
    }
}