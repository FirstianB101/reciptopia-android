package com.ich.reciptopia.presentation.board.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoardScreen(

){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BackHandler{
        if(bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
            ) {
                BoardCommentBottomSheet()
            }
        }, sheetPeekHeight = 0.dp
    ) {
        BoardDetailScreen(
            modifier = Modifier.fillMaxSize(),
            onCommentClicked = {
                scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
            }
        )
    }
}