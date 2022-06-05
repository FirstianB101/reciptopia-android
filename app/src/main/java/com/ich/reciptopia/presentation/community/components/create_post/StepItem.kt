package com.ich.reciptopia.presentation.community.components.create_post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.R

@Composable
fun StepItem(
    modifier: Modifier = Modifier,
    index: Int,
    step: Step,
    description: String,
    endIcon: ImageVector,
    backgroundColor: Color,
    contentColor: Color,
    onDescriptionChange: (String) -> Unit,
    onIconClick: (() -> Unit)?,
    onImageClick: () -> Unit
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
                text = "$index",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .border(1.dp, contentColor, RoundedCornerShape(10))
                    .clickable{onImageClick()},
                painter = if(step.pictureUrl == null) painterResource(id = R.drawable.ic_baseline_image_24)
                          else rememberImagePainter(step.pictureUrl),
                contentDescription = "",
                contentScale = if(step.pictureUrl == null) ContentScale.Inside else ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(backgroundColor)
                    .border(1.dp, contentColor, RoundedCornerShape(10))
                    .padding(4.dp),
                value = description,
                onValueChange = onDescriptionChange
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (onIconClick != null) {
                IconButton(
                    modifier = Modifier.size(18.dp),
                    onClick = onIconClick
                ) {
                    Icon(
                        imageVector = endIcon,
                        contentDescription = "",
                        tint = contentColor
                    )
                }
            }else{
                Icon(
                    imageVector = endIcon,
                    contentDescription = "",
                    tint = contentColor
                )
            }
        }
    }
}