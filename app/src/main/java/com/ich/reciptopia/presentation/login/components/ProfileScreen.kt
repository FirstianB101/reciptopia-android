package com.ich.reciptopia.presentation.login.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ich.reciptopia.R

@Composable
fun ProfileScreen(
    navController: NavController
){
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

        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile Login Icon",
            tint = colorResource(id = R.color.main_color)
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(30))
                .padding(4.dp),
            text = "nickname",
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
            onClick = {}
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.save),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}