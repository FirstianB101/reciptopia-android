package com.ich.reciptopia.presentation.community.components.create_post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.VerticalChips
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun IngredientNameWithDetailDialog(
    modifier: Modifier = Modifier,
    title: String,
    buttonText: String,
    dialogState: Boolean,
    onDismiss: () -> Unit,
    onButtonClick: (List<ChipState>) -> Unit
) {

    if (dialogState) {
        var name by rememberSaveable { mutableStateOf("") }
        var detail by rememberSaveable { mutableStateOf("") }
        val chips = remember { mutableStateListOf<ChipState>()}

        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        text = name,
                        onValueChange = {name = it},
                        placeholderText = stringResource(id = R.string.comment_input_ingredient_name)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        text = detail,
                        onValueChange = {detail = it},
                        placeholderText = stringResource(id = R.string.comment_input_ingredient_detail)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            chips.add(
                                ChipState(
                                    text = name,
                                    isSubIngredient = mutableStateOf(true),
                                    detail = detail
                                )
                            )
                            name = ""
                            detail = ""
                        }
                    ) {
                        Text(
                            text = "재료 추가"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(8.dp))

                    VerticalChips(
                        modifier = Modifier.weight(1f),
                        chipStates = chips,
                        onChipClicked = { text, isMain, idx ->
                            val previous = chips[idx].isSubIngredient.value
                            chips[idx].isSubIngredient.value = !previous
                        },
                        onImageClicked = { text, isMain, idx ->
                            chips.removeAt(idx)
                        }
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            onButtonClick(chips)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = buttonText
                        )
                    }
                }
            }
        }
    }
}