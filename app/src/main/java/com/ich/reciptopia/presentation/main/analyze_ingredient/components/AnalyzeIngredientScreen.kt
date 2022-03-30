package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.ich.reciptopia.common.util.Constants

@Composable
fun AnalyzeIngredientScreen(
){
    val context = LocalContext.current
    val images = remember { mutableStateListOf<Bitmap>() }
    var currentImageCnt by remember { mutableStateOf(0) }

    var manageDialogState by remember { mutableStateOf(false) }
    var analyzeDialogState by remember { mutableStateOf(false) }

    Box {
        CameraView(
            currentImageCount = currentImageCnt,
            onImageCaptured = { bitmap, fromGallery ->
                if(currentImageCnt < Constants.MAX_IMAGE_CNT) {
                    images.add(bitmap)
                    currentImageCnt++
                }
            },
            onError = {
                it.printStackTrace()
            },
            onImageButtonClicked = {
                manageDialogState = true
            },
            onAnalyzeButtonClicked = {
                analyzeDialogState = true
            }
        )
    }

    ManageImageDialog(
        showDialog = manageDialogState,
        images = images,
        numOfImages = currentImageCnt,
        onDeleteImage = { checkedBitmaps ->
            for(bitmap in checkedBitmaps){
                images.remove(bitmap)
            }
            currentImageCnt = images.size
            Toast.makeText(context, "선택한 사진을 제외했습니다", Toast.LENGTH_SHORT).show()
        },
        onAnalyzeButtonClicked = {
            analyzeDialogState = true
            manageDialogState = false
        }
    ) {
        manageDialogState = false
    }

    AnalyzeResultDialog(
        showDialog = analyzeDialogState
    ) {
        analyzeDialogState = false
    }
}