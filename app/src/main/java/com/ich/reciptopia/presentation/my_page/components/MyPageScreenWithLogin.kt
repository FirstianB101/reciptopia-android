package com.ich.reciptopia.presentation.my_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.presentation.my_page.MyPageScreens
import com.ich.reciptopia.presentation.my_page.profile.ProfileViewModel

@Composable
fun MyPageScreenWithLogin(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.mypage),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(MyPageScreens.ProfileScreen.route) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "MyPage Login Icon",
                tint = colorResource(id = R.color.main_color)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = state.value.curAccount?.nickname ?: "",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = state.value.curAccount?.email ?: "",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xDDDDDDDD)
            ),
            onClick = { navController.navigate(MyPageScreens.ProfileScreen.route) }
        ) {
            Text(
                text = stringResource(id = R.string.edit_profile),
                color = Color.Black
            )
        }

        Divider()
        
        Spacer(modifier = Modifier.weight(1f))

        Divider()

        TextButton(
            onClick = {
                ReciptopiaApplication.instance!!.logout()
                navController.navigate(MyPageScreens.MyPageWithoutLogin.route)
            }
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                color = Color.Gray,
                fontSize = 17.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}