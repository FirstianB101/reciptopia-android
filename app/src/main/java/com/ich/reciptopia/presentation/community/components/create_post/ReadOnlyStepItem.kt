package com.ich.reciptopia.presentation.community.components.create_post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.ui.theme.ReciptopiaTheme

@Composable
fun ReadOnlyStepItem(
    modifier: Modifier = Modifier,
    index: Int,
    step: Step,
    backgroundColor: Color,
    contentColor: Color
){
    Card(
        modifier = modifier
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.width(20.dp),
                text = "$index",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .border(1.dp, contentColor, RoundedCornerShape(10)),
                painter = rememberImagePainter(step.pictureUrl),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(backgroundColor)
                    .padding(top = 8.dp, bottom = 4.dp, start = 4.dp, end = 4.dp),
                text = step.description ?: "",
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun ReadOnlyStepItemPreview(){
    ReciptopiaTheme {
        ReadOnlyStepItem(
            index = 1,
            step = Step(description = "조리과정 1"),
            backgroundColor = Color.White,
            contentColor = Color.LightGray
        )
    }
}