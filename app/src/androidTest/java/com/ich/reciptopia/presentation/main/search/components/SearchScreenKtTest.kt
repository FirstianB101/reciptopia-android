package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ich.reciptopia.MainActivity
import com.ich.reciptopia.common.util.TestTags
import com.ich.reciptopia.di.AppModule
import com.ich.reciptopia.di.RepositoryModule
import com.ich.reciptopia.di.UseCaseModule
import com.ich.reciptopia.domain.use_case.search_history.SearchHistoryUseCases
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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

        }
    }

    @Test
    fun clickSearchNameButton_showSearchScreen(){
        composeRule.onNodeWithTag(TestTags.SEARCH_WITH_NAME).performClick()

        composeRule.onNodeWithTag(TestTags.SEARCH_SCREEN).assertExists()

        composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).performTextInput("test1")
        composeRule.onNodeWithTag(TestTags.ADD_INGREDIENT_TEXT_FIELD).assertTextEquals("test1")
    }
}