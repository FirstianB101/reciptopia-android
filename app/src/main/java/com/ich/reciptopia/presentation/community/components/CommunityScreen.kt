package com.ich.reciptopia.presentation.community.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.community.CommunityViewModel

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
    onLoginButtonClicked: () -> Unit
){
    val state = viewModel.state.collectAsState()

    BackHandler(state.value.searchMode) {
        viewModel.onEvent(CommunityScreenEvent.SearchModeOff)
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            CommunityTopBar(
                modifier = Modifier.fillMaxWidth(),
                searchMode = state.value.searchMode,
                searchText = state.value.searchQuery,
                onLoginButtonClicked = onLoginButtonClicked,
                onSearchTextChanged = { viewModel.onEvent(CommunityScreenEvent.SearchQueryChanged(it)) },
                onSearchButtonClicked = { viewModel.onEvent(CommunityScreenEvent.SearchButtonClicked) }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            BoardListScreen()

            FloatingActionButton(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-16).dp, y = (-16).dp),
                onClick = { viewModel.onEvent(CommunityScreenEvent.CreateBoardStateChanged(true)) },
                backgroundColor = colorResource(id = R.color.main_color),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Community Write FAB"
                )
            }
        }
    }

    CreateBoardDialog(
        showDialog = state.value.showCreateBoardDialog
    ) {
        viewModel.onEvent(CommunityScreenEvent.CreateBoardStateChanged(false))
    }
}