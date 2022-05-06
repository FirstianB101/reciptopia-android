package com.ich.reciptopia.presentation.board_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun BoardDetailScreen(
    modifier: Modifier = Modifier,
    starFilled: Boolean = false,
    onCommentClicked: () -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, start = 16.dp),
                text = "Title Text",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if(starFilled) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Star Icon",
                    tint = if(starFilled) Color.Yellow else Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(
                modifier = Modifier.padding(8.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    tint = colorResource(id = R.color.main_color)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "username",
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "조회수 10",
                color = Color.Gray
            )
        }

        Divider()

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            items(4){
                Image(
                    modifier = Modifier.size(200.dp),
                    imageVector = Icons.Filled.Image,
                    contentDescription = ""
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 12.dp),
            text = "재료",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        LazyColumn(
            modifier = Modifier.padding(start = 24.dp)
        ){
            items(8){
                Text(text = "Ingredient..")
            }
        }

        Text(
            modifier = Modifier.padding(12.dp),
            text = "레시피",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = "soooooooooooooooooooooooooooo\nLooooooooooooooooo\n" +
                    "oooooooooong\n recipeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {  }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = Color.Gray
                )
                
                Text(
                    text = " 123",
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            
            TextButton(
                onClick = onCommentClicked
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Chat,
                    contentDescription = "",
                    tint = Color.Gray
                )

                Text(
                    text = " 123",
                    color = Color.Gray
                )
            }
        }
    }
}