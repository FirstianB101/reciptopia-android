package com.ich.reciptopia.presentation.my_page.profile.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.my_page.profile.ProfileScreenEvent
import com.ich.reciptopia.presentation.my_page.profile.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is ProfileViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is ProfileViewModel.UiEvent.CloseEditDialog -> {
                    viewModel.onEvent(ProfileScreenEvent.EditDialogStateChanged(false))
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                text = stringResource(id = R.string.edit_profile),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        if(state.value.profileImage != null){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                bitmap = state.value.profileImage!!.asImageBitmap(),
                contentDescription = "profile image",
                contentScale = ContentScale.Crop
            )
        }else {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile Login Icon",
                tint = colorResource(id = R.color.main_color)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(30))
                .padding(4.dp),
            text = state.value.curAccount?.nickname ?: "",
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.main_color),
                contentColor = Color.White
            ),
            onClick = {
                viewModel.onEvent(ProfileScreenEvent.EditDialogStateChanged(true))
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.edit_profile),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }

    EditAccountDialog(
        modifier = Modifier.padding(16.dp),
        initialNickname = state.value.curAccount?.nickname ?: "",
        initialProfileImage = state.value.profileImage,
        title = stringResource(id = R.string.edit_profile),
        buttonText = stringResource(id = R.string.modify),
        dialogState = state.value.showEditDialogState,
        onDismiss = { viewModel.onEvent(ProfileScreenEvent.EditDialogStateChanged(false)) },
        onButtonClick = { nickname, image ->
            viewModel.onEvent(ProfileScreenEvent.EditProfile(nickname, image))
        }
    )
}