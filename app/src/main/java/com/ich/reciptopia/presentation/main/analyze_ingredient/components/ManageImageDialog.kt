package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageImageDialog(
    showDialog: Boolean,
    images: List<Bitmap>,
    numOfImages: Int,
    onDeleteImage: (List<Bitmap>) -> Unit,
    onAnalyzeButtonClicked: () -> Unit,
    onClose: () -> Unit
){
    if(showDialog){
        var deleteModeState by rememberSaveable { mutableStateOf(false) }
        val deleteImageStates = List(images.size){ rememberSaveable { mutableStateOf(false) } }

        Dialog(onDismissRequest = onClose) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ){
                        Box (
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(16.dp),
                                text = "사진 관리 (${numOfImages}장)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            TextButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp),
                                onClick = {
                                    deleteModeState = !deleteModeState
                                    if(!deleteModeState){
                                        val selectedBitmaps = images.filterIndexed { index, _ ->
                                            deleteImageStates[index].value
                                        }
                                        onDeleteImage(selectedBitmaps)
                                    }
                                }
                            ) {
                                Text(
                                    text = if(deleteModeState) "완료" else "삭제",
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        LazyVerticalGrid(
                            modifier = Modifier.weight(1f),
                            cells = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp)
                        ){
                            items(images.size){ idx ->
                                if(idx < images.size) {
                                    CapturedImageItem(
                                        image = images[idx],
                                        contentDescription = "Manage Image",
                                        showCheckIcon = deleteModeState,
                                        isChecked = deleteImageStates[idx].value,
                                        onCheckedChanged = {
                                            deleteImageStates[idx].value = it
                                        }
                                    )
                                }
                            }
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.main_color),
                                contentColor = Color.White
                            ),
                            onClick = onAnalyzeButtonClicked
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = stringResource(id = R.string.analyze_pictures,numOfImages),
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}