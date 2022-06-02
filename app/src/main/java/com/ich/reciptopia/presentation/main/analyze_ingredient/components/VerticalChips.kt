package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun LongChip(
    text: String,
    imageId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onChipClicked: (String, Boolean) -> Unit,
    onImageClicked: (String, Boolean) -> Unit
){
    Surface(
        color = when {
            selected -> colorResource(id = R.color.sub_ingredient)
            else -> colorResource(id = R.color.main_ingredient)
        },
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> colorResource(id = R.color.sub_ingredient)
                else -> colorResource(id = R.color.main_ingredient)
            }
        ),
        modifier = modifier.clickable { onChipClicked(text, selected) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { onImageClicked(text, selected) }
            )
        }
    }
}

@Composable
fun VerticalChips(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onImageClicked: (String, Boolean, Int) -> Unit
){
    LazyColumn(modifier = modifier) {
        items(chipStates.size){ idx ->
            LongChip(
                modifier = Modifier.fillMaxWidth(),
                imageId = R.drawable.close,
                text = chipStates[idx].text,
                selected = chipStates[idx].isSubIngredient.value,
                onChipClicked = { content, isMain ->
                    onChipClicked(content,isMain,idx)
                },
                onImageClicked = { content, isMain ->
                    onImageClicked(content, isMain, idx)
                }
            )
            Spacer(modifier = Modifier.padding(6.dp))
        }
    }
}

@Composable
fun AddChip(
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    onChipAdd: (String) -> Unit
){
    var textInputMode by remember { mutableStateOf(false) }
    var newChipText by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.main_color)
        ),
        modifier = modifier
    ) {
        if(!textInputMode){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { textInputMode = true },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.main_color))
                )
            }
        }else{
            BasicTextField(
                modifier = modifier.offset(x = 10.dp),
                value = newChipText,
                onValueChange = { newChipText = it },
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (newChipText.isEmpty()) Text(
                                placeholderText,
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = 16.sp
                                )
                            )
                            innerTextField()
                        }

                        IconButton(
                            modifier = Modifier.offset(x = (-2).dp),
                            onClick = {
                                onChipAdd(newChipText)
                                newChipText = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "",
                                tint = colorResource(id = R.color.main_color)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AddableVerticalChips(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onImageClicked: (String, Boolean, Int) -> Unit,
    onChipAdd: (String) -> Unit
){
    LazyColumn(modifier = modifier) {
        items(chipStates.size){ idx ->
            LongChip(
                modifier = Modifier.fillMaxWidth(),
                imageId = R.drawable.close,
                text = chipStates[idx].text,
                selected = chipStates[idx].isSubIngredient.value,
                onChipClicked = { content, isMain ->
                    onChipClicked(content,isMain,idx)
                },
                onImageClicked = { content, isMain ->
                    onImageClicked(content, isMain, idx)
                }
            )
            Spacer(modifier = Modifier.padding(6.dp))
        }
        item {
            AddChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                placeholderText = "재료를 입력하세요",
                onChipAdd = onChipAdd
            )
        }
    }
}