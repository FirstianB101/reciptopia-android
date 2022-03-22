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
import com.ich.reciptopia.R

@Composable
fun SignupScreen(){
    var emailId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.signup),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

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
                .padding(top = 4.dp, start = 16.dp, end = 16.dp),
            value = password,
            onValueChange = { password = it },
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
            value = passwordCheck,
            onValueChange = { passwordCheck = it },
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
            value = nickname,
            onValueChange = { nickname = it },
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