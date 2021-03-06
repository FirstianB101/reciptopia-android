package com.ich.reciptopia.presentation.main.search.components

//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.navigation.compose.rememberNavController
//import com.ich.reciptopia.MainActivity
//import com.ich.reciptopia.R
//import com.ich.reciptopia.common.util.TestTags
//import com.ich.reciptopia.di.AppModule
//import com.ich.reciptopia.di.RepositoryModule
//import com.ich.reciptopia.di.UseCaseModule
//import com.ich.reciptopia.presentation.main.components.MainNavigation
//import com.ich.reciptopia.presentation.main.search.util.ChipInfo
//import com.ich.reciptopia.ui.theme.ReciptopiaTheme
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import dagger.hilt.android.testing.HiltTestApplication
//import dagger.hilt.android.testing.UninstallModules
//import junit.framework.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.robolectric.annotation.Config
//
//@UninstallModules(AppModule::class, RepositoryModule::class, UseCaseModule::class)
//@HiltAndroidTest
//@Config(application = HiltTestApplication::class)
//class SearchScreenKtTest {
//
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    val composeRule = createAndroidComposeRule<MainActivity>()
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//
//        composeRule.setContent {
//            val navController = rememberNavController()
//            ReciptopiaTheme {
//                MainNavigation(
//                    navController = navController,
//                    loginButtonClicked = {},
//                    notificationButtonClicked = {}
//                )
//            }
//        }
//    }
//
//    @Test
//    fun clickSearchNameButton_showSearchScreen(){
//        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()
//
//        composeRule.onNodeWithTag(TestTags.SEARCH_SCREEN).assertExists()
//    }
//
//    @Test
//    fun addIngredients_createChips(){
//        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()
//
//        // ?????? 10??? ??????
//        (1..10).forEach { i ->
//            // ?????? ?????? ??????
//            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).performTextInput("ingredient $i")
//
//            // ?????? ?????? ??????
//            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_BUTTON).performClick()
//        }
//
//        (1..10).forEach { i ->
//            // ?????? ???????????? ?????????
//            composeRule.onNodeWithTag(TestTags.CHIP_ROW).performScrollToIndex(i - 1)
//
//            // ???????????? chip ???????????? ??????
//            composeRule.onNodeWithText("ingredient $i").assertIsDisplayed()
//        }
//
//        // ?????? ?????? ????????? ?????? (???????????? ??????)
//        (1..10).forEach { i ->
//            composeRule.onNodeWithTag(TestTags.CHIP_ROW).performScrollToIndex(i - 1)
//
//            if(i % 2 == 0){
//                composeRule.onNodeWithText("ingredient $i").performClick()
//
//                composeRule.onNodeWithText("ingredient $i").assertBackgroundColor(
//                    Color(composeRule.activity.getColor(R.color.main_ingredient))
//                )
//            }
//        }
//    }
//
//    @Test
//    fun searchButtonClickAfterAddIngredients_createSearchHistory(){
//        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()
//
//        val info = mutableListOf<ChipInfo>()
//
//        (1..5).forEach { i ->
//            val ingredientName = "food $i"
//
//            // ?????? ?????? ??????
//            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).performTextInput(ingredientName)
//
//            // ?????? ?????? ??????
//            composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_BUTTON).performClick()
//
//            info.add(ChipInfo(ingredientName,true))
//        }
//
//        // ?????? ?????? ??????
//        composeRule.onNodeWithTag(TestTags.SEARCH_SCREEN_SEARCH_BUTTON).performClick()
//
//        // ?????? ?????? ???????????? ???????????? ???????????? ?????? ?????? ???????????? ?????????
//        composeRule.runOnUiThread {
//            composeRule.activity.onBackPressed()
//        }
//
//        // ?????? ?????? ?????? ??????
//        val newHistory = SearchHistoryEntity(info)
//        composeRule.onNodeWithText(newHistory.toString()).assertIsDisplayed()
//    }
//
//    fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
//        val capturedName = captureToImage().colorSpace.name
//        assertEquals(expectedBackground.colorSpace.name, capturedName)
//    }
//}