package com.ich.reciptopia.presentation.community.components.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.R
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.presentation.community.utils.move

@Composable
fun StepInputDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean,
    initialValue: List<Step>,
    onDismiss: () -> Unit,
    onButtonClick: (List<Step>) -> Unit
){
    if(dialogState){
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp)
            ){
                var step by remember { mutableStateOf(Step(description = "")) }
                val steps = remember { mutableStateListOf<Step>().also{it.addAll(initialValue)} }

                val galleryLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    if(uri != null) {
                        step = step.copy(pictureUrl = uri.toString())
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "조리 순서",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider()

                    StepItem(
                        modifier = Modifier.fillMaxWidth(),
                        step = step,
                        index = steps.size + 1,
                        endIcon = Icons.Default.Add,
                        onDescriptionChange = { step = step.copy(description = it) },
                        description = step.description ?: "",
                        onIconClick = {
                            steps.add(
                                Step(
                                    description = step.description,
                                    pictureUrl = step.pictureUrl
                                )
                            )
                            step = step.copy(
                                description = "",
                                pictureUrl = null
                            )
                        },
                        backgroundColor = colorResource(id = R.color.selected_bg),
                        contentColor = colorResource(id = R.color.main_color),
                        onImageClick = { galleryLauncher.launch("image/*") }
                    )

                    Divider()

                    DragDropList(
                        modifier = Modifier.weight(1f),
                        items = steps,
                        onMove = { fromIndex, toIndex -> steps.move(fromIndex, toIndex)},
                        onDescriptionChange = { idx, description ->
                            steps[idx] = steps[idx].copy(description = description)
                        },
                        pictureChange = { idx, uri ->
                            steps[idx] = steps[idx].copy(pictureUrl = uri)
                        },
                        deleteStep = {
                            steps.removeAt(it)
                        }
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onButtonClick(steps) }
                    ) {
                        Text(text = "Complete")
                    }
                }
            }
        }
    }
}