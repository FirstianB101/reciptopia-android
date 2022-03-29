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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoardScreen(

){
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    BackHandler(bottomState.isVisible) {
        scope.launch {
            bottomState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
            ) {
                BoardCommentBottomSheet()
            }
        }
    ) {
        BoardDetailScreen(
            modifier = Modifier.fillMaxSize(),
            onCommentClicked = {
                scope.launch { bottomState.animateTo(ModalBottomSheetValue.Expanded) }
            }
        )
    }
}