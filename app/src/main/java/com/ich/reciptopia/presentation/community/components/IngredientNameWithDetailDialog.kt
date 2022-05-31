package com.ich.reciptopia.presentation.community.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.presentation.my_page.profile.components.DialogCustomTextField
import com.ich.reciptopia.ui.theme.ReciptopiaTheme

@Composable
fun IngredientNameWithDetailDialog(
    modifier: Modifier = Modifier,
    title: String,
    buttonText: String,
    dialogState: Boolean,
    onDismiss: () -> Unit,
    onButtonClick: (String, String) -> Unit
) {
    if (dialogState) {
        var name by rememberSaveable { mutableStateOf("") }
        var detail by rememberSaveable { mutableStateOf("") }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Column {
                    DialogCustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        caption = "재료 이름",
                        value = name,
                        onValueChange = { name = it }
                    )

                    DialogCustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        caption = "부가 정보",
                        value = detail,
                        onValueChange = { detail = it }
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            onButtonClick(name, detail)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = buttonText
                        )
                    }
                }
            }
        )
    }
}