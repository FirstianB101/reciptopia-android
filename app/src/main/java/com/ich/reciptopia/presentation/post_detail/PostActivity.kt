package com.ich.reciptopia.presentation.post_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ich.reciptopia.presentation.post_detail.components.PostScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : ComponentActivity() {

    companion object{
        fun getPostIntent(context: Context): Intent {
            return Intent(context, PostActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedPostId = intent.getLongExtra("selectedPostId",-1L)
        setContent {
            ReciptopiaTheme {
                PostScreen(selectedPostId)
            }
        }
    }
}