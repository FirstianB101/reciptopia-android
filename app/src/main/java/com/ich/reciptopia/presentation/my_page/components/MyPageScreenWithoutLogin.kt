package com.ich.reciptopia.presentation.my_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.my_page.MyPageScreens

@Composable
fun MyPageScreenWithoutLogin(
    navController: NavController
){
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
                .clickable { navController.navigate(MyPageScreens.LoginScreen.route) }
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

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.comment_require_login),
                color = Color.Gray,
                fontSize = 18.sp
            )

            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.ArrowRight,
                contentDescription = "MyPage Arrow Icon",
                tint = Color.Gray
            )
        }

        Divider()
    }
}