package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.TestTags

@Composable
fun ChipWithImage(
    text: String,
    imageId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onChipClicked: (String, Boolean) -> Unit,
    onImageClicked: (String, Boolean) -> Unit
) {
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
        modifier = modifier
    ) {
        Row(modifier = Modifier) {
            Text(
                text = text,
                style = typography.body2,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onChipClicked(text, selected) }
            )
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
fun Chips(
    modifier: Modifier = Modifier,
    elements: List<String>,
    selectStates: List<Boolean>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onImageClicked: (String, Boolean, Int) -> Unit
) {
    LazyRow(
        modifier = modifier.testTag(TestTags.CHIP_ROW)
    ) {
        items(elements.size){ idx ->
            ChipWithImage(
                text = elements[idx],
                imageId = R.drawable.close,
                selected = selectStates[idx],
                onChipClicked = { content, isMain ->
                    onChipClicked(content,isMain,idx)
                },
                onImageClicked = { content, isMain ->
                    onImageClicked(content, isMain, idx)
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}