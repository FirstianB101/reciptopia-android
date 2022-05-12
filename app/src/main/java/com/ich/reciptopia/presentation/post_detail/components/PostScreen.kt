package com.ich.reciptopia.presentation.post_detail.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.presentation.post_detail.PostDetailEvent
import com.ich.reciptopia.presentation.post_detail.PostDetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    selectedPostId: Long,
    viewModel: PostDetailViewModel = hiltViewModel()
){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.initialize(selectedPostId)
    }

    BackHandler{
        if(bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }else{
            (context as Activity).finish()
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
                PostCommentBottomSheet(
                    postId = selectedPostId,
                    viewModel = viewModel
                )
            }
        }, sheetPeekHeight = 0.dp
    ) {
        PostDetailScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel,
            onCommentClicked = {
                scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
            }
        )
    }
}