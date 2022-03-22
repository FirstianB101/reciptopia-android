package com.ich.reciptopia.presentation.main.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.ChipState
import com.ich.reciptopia.presentation.main.search.util.NormalChipState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    onChipClicked: (String, Boolean, Int) -> Unit,
    onDeleteClicked: (String, Boolean, Int) -> Unit,
    onChipReset: () -> Unit
){
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        stringResource(id = R.string.search_history),
        stringResource(id = R.string.favorite)
    )

    var testStates by remember { mutableStateOf(listOf(
        listOf(NormalChipState("검색 메인 재료1",false),NormalChipState("검색 서브 재료1",true)),
        listOf(NormalChipState("검색 메인 재료2",false),NormalChipState("검색 서브 재료2",true)),
        listOf(NormalChipState("검색 메인 재료3",false),NormalChipState("검색 서브 재료3",true)),
    )) }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
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
                    0 -> {
                        for(st in testStates) {
                            SearchHistoryListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                items = st
                            )

                            Divider()
                        }
                    }
                    1 -> {
                        for(i in 0..4) {
                            FavoriteListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp)
                            )

                            Divider()
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-16).dp, y = (-16).dp),
            onClick = {
                testStates = testStates.toMutableList().also {
                    val newChips = mutableListOf<NormalChipState>()
                    for(st in chipStates){
                        newChips.add(NormalChipState(st.text,st.isSubIngredient.value))
                    }
                    it.add(0,newChips)
                }
                onChipReset()
            },
            backgroundColor = colorResource(id = R.color.main_color),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search FAB"
            )
        }
    }
}