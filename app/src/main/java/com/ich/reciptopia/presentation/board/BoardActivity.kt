package com.ich.reciptopia.presentation.board

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.ich.reciptopia.presentation.board.components.BoardDetailScreen
import com.ich.reciptopia.presentation.board.components.BoardScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme

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