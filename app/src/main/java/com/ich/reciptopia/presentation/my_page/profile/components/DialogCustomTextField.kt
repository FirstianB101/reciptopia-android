package com.ich.reciptopia.presentation.my_page.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R

@Composable
fun DialogCustomTextField(
    modifier: Modifier = Modifier,
    caption: String,
    value: String,
    onValueChange: (String) -> Unit
){
    Column (
        modifier = modifier
    ){
        val maxLength = 50
        val blue = Color(0xff76a9ff)
        Text(
            text = caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = blue
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.light_blue),
                cursorColor = Color.Black,
                disabledLabelColor = colorResource(id = R.color.light_blue),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Text(
            text = "${value.length} / $maxLength",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            color = blue
        )
    }
}