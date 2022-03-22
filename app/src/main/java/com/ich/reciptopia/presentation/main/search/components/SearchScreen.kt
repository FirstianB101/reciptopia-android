package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onDeleteClicked: (String, Boolean, Int) -> Unit
){
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        stringResource(id = R.string.search_history),
        stringResource(id = R.string.favorite)
    )

    Column(
        modifier = modifier
    ) {
        Chips(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elements = chipStates.map{ s->s.text },
            selectStates = chipStates.map{ s->s.isSubIngredient.value },
            onChipClicked = onChipClicked,
            onImageClicked = onDeleteClicked
        )

        Column {
            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title, maxLines = 1, softWrap = false)},
                    )
                }
            }
            when (tabIndex) {
                0 -> Text("검색 기록 목록")
                1 -> Text("즐겨찾기 목록")
            }
        }
    }
}