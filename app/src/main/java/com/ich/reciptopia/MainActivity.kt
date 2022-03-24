package com.ich.reciptopia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.ich.reciptopia.presentation.main.components.MainScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReciptopiaTheme {
                val permissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)

                LaunchedEffect(Unit){
                    if(permissionState.status is PermissionStatus.Denied){
                        permissionState.launchPermissionRequest()
                    }
                }

                MainScreen()
            }
        }
    }
}