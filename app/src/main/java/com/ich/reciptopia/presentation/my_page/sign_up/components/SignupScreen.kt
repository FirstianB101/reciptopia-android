package com.ich.reciptopia.presentation.my_page.sign_up.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.common.components.OneButtonDialog
import com.ich.reciptopia.presentation.my_page.sign_up.SignUpScreenEvent
import com.ich.reciptopia.presentation.my_page.sign_up.SignUpViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LaunchedEffect(Unit){
            viewModel.eventFlow.collectLatest { event ->
                when(event){
                    is SignUpViewModel.UiEvent.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignUpViewModel.UiEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(event.message)
                    }
                    is SignUpViewModel.UiEvent.SignUpSuccess -> {
                        viewModel.onEvent(SignUpScreenEvent.ShowNotification(true))
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
                    text = stringResource(id = R.string.signup),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Image(
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 16.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Signup Screen Main Icon"
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                value = state.value.email,
                onValueChange = { viewModel.onEvent(SignUpScreenEvent.EmailChanged(it)) },
                label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        color = Color.Gray
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                value = state.value.password,
                onValueChange = { viewModel.onEvent(SignUpScreenEvent.PasswordChanged(it)) },
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        color = Color.Gray
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                value = state.value.passwordCheck,
                onValueChange = { viewModel.onEvent(SignUpScreenEvent.PasswordCheckChanged(it)) },
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(
                        text = stringResource(id = R.string.password_check),
                        color = Color.Gray
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                value = state.value.nickname,
                onValueChange = { viewModel.onEvent(SignUpScreenEvent.NicknameChanged(it)) },
                label = {
                    Text(
                        text = stringResource(id = R.string.nickname),
                        color = Color.Gray
                    )
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = {
                    viewModel.onEvent(SignUpScreenEvent.SignUp)
                }
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(id = R.string.signup),
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter,
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        onClick = {

                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "소셜 회원가입",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    OneButtonDialog(
        modifier = Modifier.padding(16.dp),
        title = stringResource(id = R.string.signup_success),
        content = stringResource(id = R.string.comment_login_with_information),
        buttonText = stringResource(id = R.string.login),
        dialogState = state.value.showNotification,
        onDismiss = { viewModel.onEvent(SignUpScreenEvent.ShowNotification(false)) }
    ) {
        navController.navigateUp()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.value.isLoading){
            CircularProgressIndicator()
        }
    }
}