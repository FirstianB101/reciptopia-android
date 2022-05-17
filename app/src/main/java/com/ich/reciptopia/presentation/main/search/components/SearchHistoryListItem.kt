package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.linkStringsWithComma

@Composable
fun SearchHistoryListItem(
    modifier: Modifier = Modifier,
    items: List<String?>,
    onItemClicked: () -> Unit,
    onDeleteItem: () -> Unit
){
    Row(
        modifier = Modifier
            .clickable{ onItemClicked() }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.light_blue)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Item Search Button",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = items.linkStringsWithComma(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onDeleteItem
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Search History Delete Icon",
                tint = colorResource(id = R.color.main_color)
            )
        }
    }
}