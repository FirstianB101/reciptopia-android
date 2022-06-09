package com.ich.reciptopia.presentation.community.components

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.components.CustomTextField

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CommunityTopBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean,
    searchText: String,
    profileImage: Bitmap? = null,
    onLoginButtonClicked: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
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
            if(profileImage != null){
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    bitmap = profileImage.asImageBitmap(),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop
                )
            }else{
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Login Icon",
                    tint = colorResource(id = R.color.main_color)
                )
            }
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
                trailingIcon = null,
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp),
                fontSize = 16.sp,
                placeholderText = stringResource(id = R.string.comment_input_text_for_search),
                interactionSource = MutableInteractionSource()
            )
        }

        IconButton(
            onClick = onSearchButtonClicked
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Community Search Icon",
                tint = Color.Black
            )
        }


    }

    if(!searchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.community),
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}