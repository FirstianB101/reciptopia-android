package com.ich.reciptopia.presentation.post_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ich.reciptopia.presentation.post_detail.components.PostScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReciptopiaTheme {
                PostScreen()
            }
        }
    }
}