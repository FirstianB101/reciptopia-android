package com.ich.reciptopia.presentation.my_page.profile.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.R

@Composable
fun EditAccountDialog(
    modifier: Modifier = Modifier,
    title: String,
    buttonText: String,
    initialNickname: String,
    initialProfileImage: Bitmap?,
    dialogState: Boolean,
    onDismiss: () -> Unit,
    onButtonClick: (String, Bitmap?) -> Unit
) {
    if (dialogState) {
        var image by remember { mutableStateOf(initialProfileImage)}
        var text by remember { mutableStateOf(initialNickname) }
        val context = LocalContext.current

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
                    image = bitmap
                }
            }
        }

        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    if(image != null){
                        Image(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .clickable { galleryLauncher.launch("image/*") },
                            bitmap = image!!.asImageBitmap(),
                            contentDescription = "edit dialog profile image",
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Icon(
                            modifier = Modifier
                                .size(120.dp)
                                .clickable { galleryLauncher.launch("image/*") },
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "edit dialog profile image",
                            tint = colorResource(id = R.color.main_color)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    DialogCustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        caption = "",
                        value = text,
                        onValueChange = { text = it }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = {
                                onButtonClick(text, image)
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = buttonText
                            )
                        }
                    }
                }
            }
        }
    }
}