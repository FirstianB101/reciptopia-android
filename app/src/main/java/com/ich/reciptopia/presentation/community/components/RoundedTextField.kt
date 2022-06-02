package com.ich.reciptopia.presentation.community.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholderText: String = "",
    onValueChange: (String) -> Unit
) {
    Surface(
        color = Color.White,
        contentColor = Color.White,
        shape = RoundedCornerShape(40),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.main_color)
        ),
        modifier = modifier
    ) {
        BasicTextField(
            modifier = modifier.offset(x = 10.dp),
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        if (text.isEmpty()) Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = 16.sp
                            )
                        )
                        innerTextField()
                    }
                }
            }
        )
    }
}