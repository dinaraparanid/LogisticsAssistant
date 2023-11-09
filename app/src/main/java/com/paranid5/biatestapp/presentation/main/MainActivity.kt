package com.paranid5.biatestapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.paranid5.biatestapp.presentation.ui.theme.BIATestAppTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val USER_KEY = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            BIATestAppTheme {
                CompositionLocalProvider(
                    LocalMainNavController provides MainNavController(navController)
                ) {
                    MainScreen(Modifier.fillMaxSize())
                }
            }
        }
    }
}