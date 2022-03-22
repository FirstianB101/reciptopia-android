package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.ChipState
import com.ich.reciptopia.presentation.main.search.util.NormalChipState

@Composable
fun SearchHistoryListItem(
    modifier: Modifier = Modifier,
    items: List<NormalChipState>?
){
    Row(
        modifier = modifier,
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

        if(items == null) {
            Text(
                text = "검색 기록"
            )
        }else{
            Text(
                buildAnnotatedString {
                    items.forEach { chip ->
                        withStyle(style = SpanStyle(
                            color = if(chip.isSubIngredient) colorResource(R.color.sub_ingredient)
                            else colorResource(id = R.color.main_ingredient)
                        )){
                            append(chip.text)
                        }
                        append(", ")
                    }
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}