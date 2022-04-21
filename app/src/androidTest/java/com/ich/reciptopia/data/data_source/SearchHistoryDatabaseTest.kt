package com.ich.reciptopia.data.data_source

import com.ich.reciptopia.di.AppModule
import com.ich.reciptopia.di.RepositoryModule
import com.ich.reciptopia.di.UseCaseModule
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.search.util.ChipInfo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class, UseCaseModule::class)
class SearchHistoryDatabaseTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: SearchHistoryDatabase

    lateinit var dao: SearchHistoryDao

    @Before
    fun setup() {
        hiltRule.inject()

        dao = database.searchHistoryDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun addNewSearchHistoryTest() = runBlocking {

        // 검색 기록 5개 추가
        val histories = mutableListOf<SearchHistory>().also { historyList ->
            (0..4).forEach { i ->
                historyList.add(
                    SearchHistory(
                        listOf(
                            ChipInfo("apple$i", true),
                            ChipInfo("orange$i", true),
                            ChipInfo("banana$i", false)
                        )
                    )
                )
            }
        }

        histories.forEach { history ->
            dao.insertSearchHistory(history)
        }

        // [4, 3, 2, 1, 0]
        val result = dao.getSearchHistories().first()

        // 입력 순서의 반대로 DB에서 결과를 받아오므로 역으로 정렬 후 비교
        histories.reverse()

        for (i in result.indices) {
            val history = result[i]

            assertEquals(history.ingredients[0].text, histories[i].ingredients[0].text)
            assertEquals(history.ingredients[0].isSubIngredient, histories[i].ingredients[0].isSubIngredient)

            assertEquals(history.ingredients[1].text, histories[i].ingredients[1].text)
            assertEquals(history.ingredients[1].isSubIngredient, histories[i].ingredients[1].isSubIngredient)

            assertEquals(history.ingredients[2].text, histories[i].ingredients[2].text)
            assertEquals(history.ingredients[2].isSubIngredient, histories[i].ingredients[2].isSubIngredient)
        }
    }

    @Test
    fun deleteSearchHistoryTest() = runBlocking {
        val histories = mutableListOf<SearchHistory>().also { historyList ->
            (0..4).forEach { i ->
                historyList.add(
                    SearchHistory(
                        listOf(
                            ChipInfo("apple$i", true),
                            ChipInfo("orange$i", true),
                            ChipInfo("banana$i", false)
                        ),
                        i
                    )
                )
            }
        }

        histories.forEach { history ->
            dao.insertSearchHistory(history)
        }

        // 1번 2번 검색 기록 제거
        dao.deleteSearchHistory(histories[1])
        dao.deleteSearchHistory(histories[2])

        histories.removeAt(2)
        histories.removeAt(1)

        // [4, 3, 0]
        val result = dao.getSearchHistories().first()

        // 입력 순서의 반대로 DB에서 결과를 받아오므로 역으로 정렬 후 비교
        histories.reverse()

        for (i in result.indices) {
            val history = result[i]

            assertEquals(history.ingredients[0].text, histories[i].ingredients[0].text)
            assertEquals(history.ingredients[0].isSubIngredient, histories[i].ingredients[0].isSubIngredient)

            assertEquals(history.ingredients[1].text, histories[i].ingredients[1].text)
            assertEquals(history.ingredients[1].isSubIngredient, histories[i].ingredients[1].isSubIngredient)

            assertEquals(history.ingredients[2].text, histories[i].ingredients[2].text)
            assertEquals(history.ingredients[2].isSubIngredient, histories[i].ingredients[2].isSubIngredient)
        }
    }
}