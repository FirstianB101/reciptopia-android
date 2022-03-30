package com.ich.reciptopia.presentation.main.community.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.board.components.BoardListScreen

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: () -> Unit
){
    var createDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
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

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.community),
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Community Search Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            BoardListScreen()

            FloatingActionButton(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-16).dp, y = (-16).dp),
                onClick = { createDialog = true },
                backgroundColor = colorResource(id = R.color.main_color),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Community Write FAB"
                )
            }
        }
    }

    CreateBoardDialog(
        showDialog = createDialog
    ) {
        createDialog = false
    }
}