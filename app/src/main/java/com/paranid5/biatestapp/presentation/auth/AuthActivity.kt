package com.paranid5.biatestapp.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.paranid5.biatestapp.presentation.theme.BIATestAppTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BIATestAppTheme {
                AuthScreen(Modifier.fillMaxSize())
            }
        }
    }
}