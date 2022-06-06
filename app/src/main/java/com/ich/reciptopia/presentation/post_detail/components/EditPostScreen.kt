package com.ich.reciptopia.presentation.post_detail.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.presentation.community.components.create_post.*
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun EditPostScreen(
    initialPost: Post,
    initialSteps: List<Step>,
    initialChips: List<ChipState>,
    onEdit: (Post, List<Step>, List<ChipState>) -> Unit
){
    val context = LocalContext.current

    val images = remember { mutableStateListOf<Bitmap>() }
    val chips = remember { mutableStateListOf<ChipState>().also { it.addAll(initialChips) } }
    var title by remember { mutableStateOf(initialPost.title ?: "")}
    var content by remember { mutableStateOf(initialPost.content ?: "")}
    val steps = remember { mutableStateListOf<Step>().also{it.addAll(initialSteps)} }
    var addChipDialogState by remember { mutableStateOf(false) }
    var stepDialogState by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            //viewModel.onEvent(CommunityScreenEvent.CreatePostAddImage(uri.toString()))
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
                text = stringResource(id = R.string.edit_recipe),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                onClick = {
                    val editedPost = initialPost.copy(title = title, content = content)
                    onEdit(editedPost, steps, chips)
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
            value = title,
            onValueChange = { title = it },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {

                    if (title.isBlank()) {
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
                }
            }
        }

        Divider()

        HorizontalAddableChips(
            modifier = Modifier.padding(4.dp, 8.dp),
            elements = chips,
            onChipClicked = { text, isMain, idx ->
                val previous = chips[idx].isSubIngredient.value
                chips[idx].isSubIngredient.value = !previous
            },
            onImageClicked = { text, isMain, idx ->
                chips.removeAt(idx)
            },
            onAddChip = {
                addChipDialogState = true
            }
        )

        Divider()

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            value = content,
            onValueChange = { content = it },
            decorationBox = { innerTextField ->
                Row(
                    Modifier.padding(16.dp)
                ) {
                    if (content.isBlank()) {
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

        TextButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            onClick = { stepDialogState = true }
        ) {
            Text(
                text = "${stringResource(id = R.string.input_step)} >",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Divider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ){
            itemsIndexed(steps) { idx, step ->
                ReadOnlyStepItem(
                    index = idx + 1,
                    step = step,
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray
                )
            }
        }

        IngredientNameWithDetailDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            title = stringResource(id = R.string.input_ingredient),
            buttonText = stringResource(id = R.string.add),
            dialogState = addChipDialogState,
            initialChips = chips,
            onDismiss = { addChipDialogState = false },
            onButtonClick = {
                chips.clear()
                chips.addAll(it)
                addChipDialogState = false
            }
        )

        StepInputDialog(
            modifier = Modifier.fillMaxSize(),
            dialogState = stepDialogState,
            onDismiss = { stepDialogState = false },
            initialValue = steps,
            onButtonClick = {
                steps.clear()
                steps.addAll(it)
                stepDialogState = false
            }
        )
    }
}