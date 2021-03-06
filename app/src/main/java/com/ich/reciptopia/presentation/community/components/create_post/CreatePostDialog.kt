package com.ich.reciptopia.presentation.community.components.create_post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.presentation.community.components.create_post.PostDialogScreen

@Composable
fun CreatePostDialog(
    showDialog: Boolean,
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
                    PostDialogScreen()
                }
            }
        }
    }
}