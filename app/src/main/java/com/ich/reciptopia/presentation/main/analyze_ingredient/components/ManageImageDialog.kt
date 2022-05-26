package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ManageImageDialog(
    showDialog: Boolean,
    images: List<Bitmap>,
    numOfImages: Int,
    onDeleteImage: (Int) -> Unit,
    onAnalyzeButtonClicked: () -> Unit,
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
                        }

                        LazyVerticalGrid(
                            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(images.size) { idx ->
                                if (idx < images.size) {
                                    CapturedImageItem(
                                        modifier = Modifier.padding(8.dp),
                                        image = images[idx],
                                        contentDescription = "Manage Image",
                                        onDeleteClick = {
                                            onDeleteImage(idx)
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