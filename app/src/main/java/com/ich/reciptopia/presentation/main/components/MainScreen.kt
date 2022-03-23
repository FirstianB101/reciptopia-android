package com.ich.reciptopia.presentation.main.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ich.reciptopia.R
import com.ich.reciptopia.common.components.ReciptopiaTabRow
import com.ich.reciptopia.presentation.login.components.LoginDialog
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.community.CommunityScreen
import com.ich.reciptopia.presentation.main.search.components.CustomTextField
import com.ich.reciptopia.presentation.main.search.components.SearchScreen
import com.ich.reciptopia.presentation.main.search.util.ChipState
import com.ich.reciptopia.presentation.main.search.util.addChipState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(

){
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val tabTexts = listOf(
        stringResource(id = R.string.search_ingredient),
        stringResource(id = R.string.community)
    )
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    var loginDialogState by remember { mutableStateOf(false) }
    var searchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    var chipStates by remember { mutableStateOf(listOf<ChipState>()) }

    BackHandler(searchMode) {
        searchMode = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                backgroundColor = Color.White
            ) {
                IconButton(
                    onClick = {
                        loginDialogState = true
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Login Icon",
                        tint = colorResource(id = R.color.main_color)
                    )
                }

                if(!searchMode)
                    Spacer(Modifier.weight(1f))

                AnimatedVisibility(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    visible = searchMode,
                    enter = scaleIn() + expandHorizontally(),
                    exit = scaleOut() + shrinkHorizontally()
                ) {
                    CustomTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.offset(x = 10.dp),
                                onClick = {
                                    chipStates = chipStates.addChipState{
                                        it.add(ChipState(searchText, mutableStateOf(true)))
                                    }
                                    searchText = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "",
                                    tint = LocalContentColor.current.copy(alpha = 0.5f)
                                )
                            }
                        },
                        modifier = Modifier
                            .background(
                                Color(0xDDDDDDDD),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(4.dp)
                            .height(36.dp),
                        fontSize = 16.sp,
                        placeholderText = "재료 추가",
                    )
                }

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification Icon",
                        tint = Color.Black
                    )
                }
            }

            if(!searchMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        onClick = { searchMode = true }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black
                        )

                        Text(
                            text = "이름으로 검색",
                            color = Color.Black,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ){
        Column(
            modifier = Modifier.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(searchMode){
                SearchScreen(
                    modifier = Modifier.weight(1f),
                    chipStates = chipStates,
                    onChipClicked = { content, isMain, idx ->
                        chipStates[idx].isSubIngredient.value = !chipStates[idx].isSubIngredient.value
                    },
                    onDeleteClicked = { content, isMain, idx ->
                        Toast.makeText(context, "$content 삭제", Toast.LENGTH_SHORT).show()
                        chipStates = chipStates.addChipState {
                            it.removeAt(idx)
                        }
                    },
                    onChipReset = {
                        chipStates = emptyList()
                    }
                )
            }else{
                HorizontalPager(
                    modifier = Modifier.weight(1f),
                    count = tabTexts.size,
                    state = pagerState
                ) { page ->
                    when(page){
                        0 -> AnalyzeIngredientScreen()
                        1 -> CommunityScreen()
                    }
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
    }

    LoginDialog(
        showDialog = loginDialogState
    ) {
        loginDialogState = false
    }
}