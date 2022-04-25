package com.ich.reciptopia.presentation.main.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ich.reciptopia.R
import com.ich.reciptopia.common.components.ReciptopiaTabRow
import com.ich.reciptopia.presentation.board.components.BoardListScreen
import com.ich.reciptopia.presentation.login.components.LoginDialog
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.community.components.CommunityScreen
import com.ich.reciptopia.presentation.main.search.components.SearchScreen
import com.ich.reciptopia.presentation.main.search.util.ChipState
import com.ich.reciptopia.presentation.notification.components.NotificationDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(

) {
    val navController = rememberNavController()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val tabTexts = listOf(
        stringResource(id = R.string.search_ingredient),
        stringResource(id = R.string.community)
    )
    val tabIndex = pagerState.currentPage

    var loginDialogState by remember { mutableStateOf(false) }
    var notificationDialogState by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            count = tabTexts.size,
            state = pagerState
        ) { page ->
            when(page){
                0 -> {
                    MainNavigation(
                        navController = navController,
                        loginButtonClicked = { loginDialogState = true },
                        notificationButtonClicked = { notificationDialogState = true }
                    )
                }
                1 -> CommunityScreen(
                    modifier = Modifier.weight(1f),
                    onLoginButtonClicked = { loginDialogState = true }
                )
            }
        }

        ReciptopiaTabRow(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(top = 8.dp, bottom = 16.dp, start = 0.dp, end = 0.dp),
            tabTexts = tabTexts,
            selectedIndex = tabIndex,
            onTabSelected = {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )
    }

    LoginDialog(
        showDialog = loginDialogState
    ) {
        loginDialogState = false
    }

    NotificationDialog(
        showDialog = notificationDialogState
    ) {
        notificationDialogState = false
    }
}