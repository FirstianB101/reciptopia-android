package com.ich.reciptopia.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun OneButtonDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    buttonText: String,
    dialogState: Boolean,
    onDismiss: () -> Unit,
    onButtonClick: () -> Unit
) {
    MaterialTheme {
        Column {
            if (dialogState) {
                AlertDialog(
                    modifier = modifier,
                    onDismissRequest = onDismiss,
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = { Text(text = content) },
                    confirmButton = {
                        TextButton(
                            colors = ButtonDefaults.buttonColors(
                                contentColor = colorResource(com.ich.reciptopia.R.color.main_color),
                                backgroundColor = Color.White
                            ),
                            onClick = onButtonClick
                        ) {
                            Text(buttonText)
                        }
                    },
                )
            }
        }
    }
}