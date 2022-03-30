package com.ich.reciptopia.presentation.main.community.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun BoardDialogScreen(

){
    val context = LocalContext.current
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    var images by remember { mutableStateOf(listOf<Bitmap>()) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            if(bitmap != null){
                images = images.toMutableList().also { it.add(bitmap) }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                text = stringResource(id = R.string.write_recipe),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                onClick = {  },
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = stringResource(id = R.string.complete),
                    color = Color.White
                )
            }
        }

        Divider()

        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = titleText,
            onValueChange = { titleText = it },
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(16.dp)
                ) {

                    if (titleText.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.comment_input_title),
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            },
        )

        Divider()

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            item{
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10)
                        )
                        .clickable { galleryLauncher.launch("image/*") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Outlined.Image,
                        contentDescription = ""
                    )

                    Text(
                        text = "${images.size} / 10"
                    )
                }
            }

            items(images.size){ idx ->
                Spacer(modifier = Modifier.width(8.dp))

                CreateBoardImageItem(
                    modifier = Modifier.size(100.dp),
                    bitmap = images[idx].asImageBitmap(),
                    imageSize = 98.dp
                ) {
                    images = images.toMutableList().also { it.removeAt(idx) }
                }
            }
        }

        Divider()

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = contentText,
            onValueChange = { contentText = it },
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(16.dp)
                ) {

                    if (contentText.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.comment_input_content),
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            },
        )
    }
}