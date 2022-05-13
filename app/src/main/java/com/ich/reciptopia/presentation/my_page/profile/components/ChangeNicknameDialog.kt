package com.ich.reciptopia.presentation.my_page.profile.components

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun ChangeNicknameDialog(
    modifier: Modifier = Modifier,
    initialNickname: String,
    dialogState: Boolean,
    onDismiss: () -> Unit,
    onButtonClick: (String) -> Unit
) {
    if (dialogState) {
        var nickname by rememberSaveable { mutableStateOf(initialNickname) }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(id = R.string.edit_nickname),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Column {
                    DialogCustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        caption = "",
                        value = nickname,
                        onValueChange = { nickname = it }
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
                            onButtonClick(nickname)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(id = R.string.modify)
                        )
                    }
                }
            }
        )
    }
}