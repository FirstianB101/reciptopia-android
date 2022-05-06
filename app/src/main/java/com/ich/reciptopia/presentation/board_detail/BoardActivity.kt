package com.ich.reciptopia.presentation.board_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ich.reciptopia.presentation.board_detail.components.BoardScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReciptopiaTheme {
                BoardScreen()
            }
        }
    }
}