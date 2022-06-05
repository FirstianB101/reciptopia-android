package com.ich.reciptopia.presentation.community.components.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.presentation.community.utils.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DragDropList(
    items: List<Step>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    onDescriptionChange: (Int, String) -> Unit,
    pictureChange: (Int, String) -> Unit,
    deleteStep: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        dragDropListState.onDrag(offset = offset)

                        if (overScrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overScrollJob = scope.launch {
                                    dragDropListState.lazyListState.scrollBy(it)
                                }
                            } ?: kotlin.run { overScrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            }
            .fillMaxSize(),
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            val galleryLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                if(uri != null){
                    pictureChange(index, uri.toString())
                }
            }

            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteStep(index)
                    }
                    false
                }
            )

            StepSwipeDismiss(
                dismissState = dismissState,
                threshold = 0.4f
            ) {
                StepItem(
                    modifier = Modifier
                        .composed {
                            val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                                index == dragDropListState.currentIndexOfDraggedItem
                            }
                            Modifier.graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                        }
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.main_color)),
                    step = item,
                    index = index + 1,
                    endIcon = Icons.Default.DragHandle,
                    description = item.description ?: "",
                    onDescriptionChange = { onDescriptionChange(index, it) },
                    onIconClick = null,
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray,
                    onImageClick = { galleryLauncher.launch("image/*") }
                )

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
