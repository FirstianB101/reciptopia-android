package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.presentation.main.utils.takePicture
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnGalleryViewClick : CameraUIAction()
    object OnSwitchCameraClick : CameraUIAction()
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

@Composable
fun CameraView(
    currentImageCount: Int,
    onImageCaptured: (Bitmap, Boolean) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onImageButtonClicked: () -> Unit,
    onAnalyzeButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            if (bitmap != null) {
                onImageCaptured(bitmap, true)
            }
        }
    }

    CameraPreviewView(
        currentImageCount = currentImageCount,
        imageCapture = imageCapture,
        lensFacing = lensFacing,
        onImageButtonClicked = onImageButtonClicked,
        onAnalyzeButtonClicked = onAnalyzeButtonClicked
    ) { cameraUIAction ->
        when (cameraUIAction) {
            is CameraUIAction.OnCameraClick -> {
                imageCapture.takePicture(onImageCaptured, onError)
            }
            is CameraUIAction.OnSwitchCameraClick -> {
                lensFacing =
                    if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
                    else
                        CameraSelector.LENS_FACING_BACK
            }
            is CameraUIAction.OnGalleryViewClick -> {
                galleryLauncher.launch("image/*")
            }
        }
    }
}

@Composable
private fun CameraPreviewView(
    currentImageCount: Int,
    imageCapture: ImageCapture,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    onImageButtonClicked: () -> Unit,
    onAnalyzeButtonClicked: () -> Unit,
    cameraUIAction: (CameraUIAction) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember { PreviewView(context) }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.createSurfaceProvider(null))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color(0xAA000000))
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        modifier = Modifier.width(80.dp),
                        onClick = onImageButtonClicked,
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "Image Icon",
                                tint = Color.White
                            )
                            
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "$currentImageCount / ${Constants.MAX_IMAGE_CNT}",
                                color = Color.White
                            )
                        }
                    }

                    Button(
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.main_color)
                        ),
                        onClick = onAnalyzeButtonClicked
                    ) {
                        Text(
                            text = "${currentImageCount}개의 재료 분석하기",
                            color = Color.White
                        )
                    }
                }
            }

            CameraControls(cameraUIAction)
        }
    }
}

@Composable
fun CameraControls(cameraUIAction: (CameraUIAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CameraControl(
            imageVector = Icons.Filled.Collections,
            contentDesc = "Gallery Icon",
            modifier= Modifier
                .size(36.dp),
            onClick = { cameraUIAction(CameraUIAction.OnGalleryViewClick) }
        )

        CameraControl(
            imageVector = Icons.Filled.PhotoCamera,
            contentDesc = "Camera Icon",
            modifier= Modifier
                .size(36.dp)
                .padding(1.dp),
                //.border(1.dp, Color.Black, CircleShape),
            onClick = { cameraUIAction(CameraUIAction.OnCameraClick) }
        )

        CameraControl(
            imageVector = Icons.Filled.FlashlightOn,
            contentDesc = "Flash Icon",
            modifier= Modifier.size(36.dp),
            onClick = { cameraUIAction(CameraUIAction.OnSwitchCameraClick) }
        )
    }
}


@Composable
fun CameraControl(
    imageVector: ImageVector,
    contentDesc: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDesc,
            modifier = modifier,
            tint = Color.Black
        )
    }
}