package com.ich.reciptopia.presentation.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.login.MyPageScreens

@Composable
fun LoginScreen(
    navController: NavController
){
    var emailId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.login),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            modifier = Modifier
                .size(120.dp)
                .padding(top = 16.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Login Screen Main Icon"
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 16.dp, end = 16.dp),
            value = emailId,
            onValueChange = { emailId = it },
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
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(id = R.string.password),
                    color = Color.Gray
                )
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = {
                navController.navigate(MyPageScreens.MyPageWithLogin.route)
            }
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.login),
                color = Color.White
            )
        }

        TextButton(
            onClick = {
                navController.navigate(MyPageScreens.FindAccountScreen.route)
            }
        ) {
            Text(
                text = stringResource(id = R.string.comment_find_password),
                color = colorResource(id = R.color.main_color)
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
                        text = "소셜 로그인",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.navigate(MyPageScreens.SignupScreen.route)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.signup),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}