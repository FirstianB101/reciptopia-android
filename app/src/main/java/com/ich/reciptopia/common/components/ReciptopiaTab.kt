package com.ich.reciptopia.common.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReciptopiaTabRow(
    modifier: Modifier = Modifier,
    tabTexts: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
){
    if(tabTexts.isNotEmpty()) {
        AnimatedContent(
            targetState = selectedIndex,
            transitionSpec = {
                if(selectedIndex > initialState){
                    slideInHorizontally{ width -> width/4 } + fadeIn() with
                            slideOutHorizontally { width -> -width/4 } + fadeOut()
                }else{
                    slideInHorizontally{ width -> -width/4 } + fadeIn() with
                            slideOutHorizontally { width -> width/4 } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { targetState ->
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val showBefore = selectedIndex >= 1 && tabTexts.size >= 2
                ReciptopiaTab(
                    modifier = Modifier.weight(1f),
                    tabText = if(showBefore) tabTexts[selectedIndex - 1] else "",
                    selected = false,
                    onClick = { onTabSelected(selectedIndex - 1) }
                )

                ReciptopiaTab(
                    modifier = Modifier.weight(1f),
                    tabText = tabTexts[targetState],
                    selected = true,
                    onClick = { onTabSelected(selectedIndex) }
                )

                val showAfter = selectedIndex < tabTexts.size - 1 && tabTexts.size >= 2
                ReciptopiaTab(
                    modifier = Modifier.weight(1f),
                    tabText = if(showAfter) tabTexts[selectedIndex + 1] else "",
                    selected = false,
                    onClick = { onTabSelected(selectedIndex + 1) }
                )
            }
        }
    }
}

@Composable
fun ReciptopiaTab(
    modifier: Modifier = Modifier,
    tabText: String,
    selected: Boolean,
    onClick: () -> Unit
){
    // Can click only when tab is not blank
    Text(
        modifier = if(tabText.isNotBlank()) modifier
            .padding(8.dp)
            .clickable { onClick() }
        else modifier
            .padding(8.dp),
        text = tabText,
        color = if(selected) Color.Black
                else Color.Gray,
        fontSize = if(selected) 17.sp else 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = if(selected) FontWeight.Bold else null
    )
}