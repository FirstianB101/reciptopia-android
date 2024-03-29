package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun AnalyzeResultDialog(
    showDialog: Boolean,
    analyzeResults: Map<String, String>?,
    onSearchRecipes: (List<ChipState>) -> Unit,
    onClose: () -> Unit
){
    if(showDialog){
        val chipStates = remember { mutableStateListOf<ChipState>()}

        LaunchedEffect(Unit){
            analyzeResults?.keys?.forEach{
                chipStates.add(ChipState(analyzeResults[it] ?: "Unknown", mutableStateOf(true)))
            }
        }

        Dialog(onDismissRequest = onClose) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box (
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(24.dp),
                                text = stringResource(id = R.string.comment_analyze_ingredients),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            buildAnnotatedString {
                                append("재료가 맞는지 확인해주세요\n")
                                withStyle(style = SpanStyle(color = colorResource(R.color.main_ingredient))){
                                    append("핵심 재료")
                                }
                                append("를 체크하면 해당 재료는 검색에 반드시 포함됩니다.")
                            },
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AddableVerticalChips(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .weight(1f)
                                .padding(16.dp),
                            chipStates = chipStates,
                            onChipClicked = { content, isMain, idx ->
                                chipStates[idx].isSubIngredient.value = !chipStates[idx].isSubIngredient.value
                            },
                            onImageClicked = {  content, isMain, idx ->
                                chipStates.removeAt(idx)
                            },
                            onChipAdd = { newChipText ->
                                chipStates.add(ChipState(newChipText, mutableStateOf(true)))
                            }
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.main_color),
                                contentColor = Color.White
                            ),
                            onClick = {
                                onSearchRecipes(chipStates)
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = stringResource(id = R.string.find_recipes),
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}