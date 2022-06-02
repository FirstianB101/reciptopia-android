package com.ich.reciptopia.presentation.community.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.components.ChipWithImage
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
private fun AddChip(
    icon: ImageVector,
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
            .clickable { onChipClicked() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = "Create Post Dialog Chip Add Icon"
            )
        }
    }
}

@Composable
fun HorizontalAddableChips(
    modifier: Modifier = Modifier,
    elements: List<ChipState>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onImageClicked: (String, Boolean, Int) -> Unit,
    onAddChip: () -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        item{
            AddChip(
                modifier = Modifier
                    .size(80.dp, 35.dp),
                icon = Icons.Filled.Add,
                backgroundColor = colorResource(id = R.color.light_blue),
                contentColor = Color.Black,
                onChipClicked = onAddChip
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
        items(elements.size){ idx ->
            ChipWithImage(
                text = elements[idx].text,
                imageId = R.drawable.close,
                selected = elements[idx].isSubIngredient.value,
                onChipClicked = { content, isMain ->
                    onChipClicked(content, isMain,idx)
                },
                onImageClicked = { content, isMain ->
                    onImageClicked(content, isMain, idx)
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}