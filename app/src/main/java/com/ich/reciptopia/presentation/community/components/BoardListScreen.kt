package com.ich.reciptopia.presentation.community.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.presentation.board_detail.BoardActivity
import com.ich.reciptopia.presentation.board_detail.components.BoardPreviewItem

@Composable
fun BoardListScreen(

){
    val options = listOf("최신순", "인기순", "모발순")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.align(Alignment.End)
        ) {
            TextButton(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    text = selectedOptionText,
                    color = Color.Black
                )
            }
            DropdownMenu(
                modifier = Modifier.width(85.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        selectedOptionText = label
                    }) {
                        Text(text = label)
                    }
                }
            }
        }

        Divider()
        BoardPreviewItem(
            modifier = Modifier.fillMaxWidth(),
            onBoardClick = {
                startBoardActivity(context)
            }
        )
        Divider()
        BoardPreviewItem(
            modifier = Modifier.fillMaxWidth(),
            starFilled = true,
            onBoardClick = {
                startBoardActivity(context)
            }
        )
        Divider()
    }
}

private fun startBoardActivity(context: Context){
    val intent = Intent(context, BoardActivity::class.java)
    context.startActivity(intent)
}