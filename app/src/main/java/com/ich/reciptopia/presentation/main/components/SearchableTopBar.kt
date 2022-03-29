package com.ich.reciptopia.presentation.main.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.components.CustomTextField
import com.ich.reciptopia.presentation.main.search.util.ChipState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchableTopBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean,
    searchText: String,
    searchSource: MutableInteractionSource,
    onLoginButtonClicked: () -> Unit,
    onAddChip: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchTextReset: () -> Unit,
    onSearchButtonClicked: () -> Unit
){
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        backgroundColor = Color.White
    ) {
        IconButton(
            onClick = onLoginButtonClicked
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Login Icon",
                tint = colorResource(id = R.color.main_color)
            )
        }

        if(!searchMode)
            Spacer(Modifier.weight(1f))

        AnimatedVisibility(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            visible = searchMode,
            enter = scaleIn() + expandHorizontally(),
            exit = scaleOut() + shrinkHorizontally()
        ) {
            CustomTextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.offset(x = 10.dp),
                        onClick = {
                            onAddChip()
                            onSearchTextReset()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "",
                            tint = LocalContentColor.current.copy(alpha = 0.5f)
                        )
                    }
                },
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp),
                fontSize = 16.sp,
                placeholderText = "재료 추가",
                interactionSource = searchSource
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notification Icon",
                tint = Color.Black
            )
        }
    }

    if(!searchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = onSearchButtonClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )

                Text(
                    text = "이름으로 검색",
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}