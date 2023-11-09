package com.paranid5.biatestapp.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.paranid5.biatestapp.presentation.ui.theme.BIATestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            BIATestAppTheme {
                CompositionLocalProvider(
                    LocalAuthNavController provides AuthNavController(navController)
                ) {
                    AuthContentScreen(Modifier.fillMaxSize())
                }
            }
        }
    }
}