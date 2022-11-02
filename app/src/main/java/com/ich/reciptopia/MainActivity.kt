package com.ich.reciptopia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.ich.reciptopia.presentation.main.components.MainScreen
import com.ich.reciptopia.ui.theme.ReciptopiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
        //token test 0C14B5C0-89F7-4E60-99FC-B7EBFFE7BDBB
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