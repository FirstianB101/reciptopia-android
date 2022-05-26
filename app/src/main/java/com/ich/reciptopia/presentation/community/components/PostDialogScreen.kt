package com.ich.reciptopia.presentation.community.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.community.CommunityViewModel
import com.ich.reciptopia.presentation.my_page.profile.components.TextInputDialog

@Composable
fun PostDialogScreen(
    viewModel: CommunityViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val images = remember { mutableStateListOf<Bitmap>() }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onEvent(CommunityScreenEvent.CreatePostAddImage(uri.toString()))
            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            if(bitmap != null) images.add(bitmap)
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
                onClick = {
                    viewModel.onEvent(CommunityScreenEvent.CreatePost)
                },
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
            value = state.value.newPostTitle,
            onValueChange = { viewModel.onEvent(CommunityScreenEvent.CreatePostTitleChanged(it)) },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {

                    if (state.value.newPostTitle.isEmpty()) {
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
                        .clickable {
                            if (images.size < Constants.MAX_IMAGE_CNT) {
                                galleryLauncher.launch("image/*")
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "이미지는 최대 10장까지 추가할 수 있습니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        },
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

                CreatePostImageItem(
                    modifier = Modifier.size(100.dp),
                    bitmap = images[idx].asImageBitmap(),
                    imageSize = 98.dp
                ) {
                    images.removeAt(idx)
                    viewModel.onEvent(CommunityScreenEvent.CreatePostRemoveImage(idx))
                }
            }
        }

        Divider()

        HorizontalAddableChips(
            modifier = Modifier.padding(4.dp, 8.dp),
            elements = state.value.newPostChips,
            onChipClicked = { text, isMain, idx ->
                viewModel.onEvent(CommunityScreenEvent.ClickChip(idx))
            },
            onImageClicked = { text, isMain, idx ->
                viewModel.onEvent(CommunityScreenEvent.RemoveChip(idx))
            },
            onAddChip = {
                viewModel.onEvent(CommunityScreenEvent.AddChipDialogStateChanged(true))
            }
        )

        Divider()

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = state.value.newPostContent,
            onValueChange = { viewModel.onEvent(CommunityScreenEvent.CreatePostContentChanged(it)) },
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(16.dp)
                ) {
                    if (state.value.newPostContent.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.comment_input_content),
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            },
        )

        Divider()

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            value = state.value.newPostStep,
            onValueChange = { viewModel.onEvent(CommunityScreenEvent.CreatePostStepChanged(it)) },
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(16.dp)
                ) {

                    if (state.value.newPostStep.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.comment_input_step),
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            },
        )

        TextInputDialog(
            modifier = Modifier.padding(16.dp),
            title = stringResource(id = R.string.input_ingredient),
            buttonText = stringResource(id = R.string.add_ingredient),
            initialValue = "",
            dialogState = state.value.showAddChipDialog,
            onDismiss = { viewModel.onEvent(CommunityScreenEvent.AddChipDialogStateChanged(false)) },
            onButtonClick = {
                viewModel.onEvent(CommunityScreenEvent.AddChip(it))
                viewModel.onEvent(CommunityScreenEvent.AddChipDialogStateChanged(false))
            }
        )
    }
}