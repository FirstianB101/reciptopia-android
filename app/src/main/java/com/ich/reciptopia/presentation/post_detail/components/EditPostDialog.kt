package com.ich.reciptopia.presentation.post_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun EditPostDialog(
    showDialog: Boolean,
    initialPost: Post,
    initialSteps: List<Step>,
    initialChips: List<ChipState>,
    onEdit: (Post, List<Step>, List<ChipState>) -> Unit,
    onClose: () -> Unit
){
    if(showDialog){
        Dialog(onDismissRequest = onClose) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    EditPostScreen(
                        initialPost = initialPost,
                        initialSteps = initialSteps,
                        initialChips = initialChips,
                        onEdit = onEdit
                    )
                }
            }
        }
    }
}