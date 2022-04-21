package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.MainActivity
import com.ich.reciptopia.common.util.TestTags
import com.ich.reciptopia.di.AppModule
import com.ich.reciptopia.di.RepositoryModule
import com.ich.reciptopia.di.UseCaseModule
import com.ich.reciptopia.domain.use_case.search_history.SearchHistoryUseCases
import com.ich.reciptopia.presentation.board.components.BoardListScreen
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.components.MainNavigation
import com.ich.reciptopia.presentation.main.components.MainScreenUI
import com.ich.reciptopia.presentation.main.components.SearchableTopBar
import com.ich.reciptopia.presentation.main.search.util.ChipState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.ich.reciptopia.R
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class, UseCaseModule::class)
class SearchScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var useCases: SearchHistoryUseCases

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.setContent {
            val navController = rememberNavController()
            var searchMode by remember { mutableStateOf(false) }
            var searchText by remember { mutableStateOf("") }
            val searchSource = remember { MutableInteractionSource() }
            val chipStates = remember { mutableStateListOf<ChipState>() }

            MainNavigation(
                navController = navController,
                cameraScreen = { AnalyzeIngredientScreen() },
                searchScreen = {
                    SearchScreen(
                        chipStates = chipStates,
                        navController = navController,
                        onChipClicked = { content, isMain, idx ->
                            chipStates[idx].isSubIngredient.value = !chipStates[idx].isSubIngredient.value
                        },
                        onDeleteClicked = { content, isMain, idx ->
                            chipStates.removeAt(idx)
                        },
                        onChipReset = {
                            chipStates.clear()
                        }
                    )
                },
                boardScreen = { BoardListScreen() },
                searchBar = {
                    SearchableTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        searchMode = searchMode,
                        searchText = searchText,
                        searchSource = searchSource,
                        onLoginButtonClicked = { },
                        onNotificationButtonClicked = { },
                        onAddChip = {
                            chipStates.add(ChipState(searchText, mutableStateOf(true)))
                        },
                        onSearchTextChanged = { searchText = it },
                        onSearchTextReset = { searchText = "" },
                        onSearchButtonClicked = {
                            searchMode = true
                            navController.navigate(MainScreenUI.SearchScreen.route)
                        }
                    )
                }
            )
        }
    }

    @Test
    fun clickSearchNameButton_showSearchScreen(){
        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()

        composeRule.onNodeWithTag(TestTags.SEARCH_SCREEN).assertExists()
    }

    @Test
    fun addIngredients_createChips(){
        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()

        // 재료 10개 추가
        (1..10).forEach { i ->
            // 재료 이름 입력
            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).performTextInput("ingredient $i")

            // 추가 버튼 클릭
            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_BUTTON).performClick()
        }
        
        (1..10).forEach { i ->
            // 다음 요소까지 스크롤
            composeRule.onNodeWithTag(TestTags.CHIP_ROW).performScrollToIndex(i - 1)
            
            // 해당하는 chip 존재여부 확인
            composeRule.onNodeWithText("ingredient $i").assertIsDisplayed()
        }

        // 짝수 번째 재료를 클릭 (메인재료 지정)
        (1..10).forEach { i ->
            composeRule.onNodeWithTag(TestTags.CHIP_ROW).performScrollToIndex(i - 1)

            if(i % 2 == 0){
                composeRule.onNodeWithText("ingredient $i").performClick()

                composeRule.onNodeWithText("ingredient $i").assertBackgroundColor(
                    Color(composeRule.activity.getColor(R.color.main_ingredient))
                )
            }
        }
    }

    @Test
    fun searchButtonClickAfterAddIngredients_createSearchHistory(){
        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()

        val info = mutableListOf<ChipInfo>()

        (1..5).forEach { i ->
            val ingredientName = "food $i"

            // 재료 이름 입력
            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).performTextInput(ingredientName)

            // 추가 버튼 클릭
            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_BUTTON).performClick()

            info.add(ChipInfo(ingredientName,true))
        }

        // 검색 버튼 클릭
        composeRule.onNodeWithTag(TestTags.SEARCH_SCREEN_SEARCH_BUTTON).performClick()

        // 검색 결과 화면에서 뒤로가기 버튼으로 검색 기록 화면으로 돌아옴
        composeRule.runOnUiThread {
            composeRule.activity.onBackPressed()
        }

        // 검색 기록 생성 확인
        val newHistory = SearchHistory(info)
        composeRule.onNodeWithText(newHistory.toString()).assertIsDisplayed()
    }

    fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
        val capturedName = captureToImage().colorSpace.name
        assertEquals(expectedBackground.colorSpace.name, capturedName)
    }
}