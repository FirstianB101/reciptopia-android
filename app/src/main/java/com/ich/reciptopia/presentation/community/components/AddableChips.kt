package com.ich.reciptopia.presentation.community.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.TestTags
import com.ich.reciptopia.presentation.main.search.components.ChipWithImage

@Composable
private fun Chip(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onChipClicked: () -> Unit,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = backgroundColor
        ),
        modifier = modifier
    ) {
        Row(modifier = Modifier) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onChipClicked() }
            )
        }
    }
}

@Composable
fun AddableChips(
    modifier: Modifier = Modifier,
    elements: List<String>,
    selectStates: List<Boolean>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onImageClicked: (String, Boolean, Int) -> Unit,
    onAddChip: () -> Unit
) {
    LazyRow(
        modifier = modifier.testTag(TestTags.CHIP_ROW)
    ) {
        item{
            Chip(
                text = "+",
                backgroundColor = colorResource(id = R.color.light_blue),
                contentColor = Color.Black,
                onChipClicked = onAddChip
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
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