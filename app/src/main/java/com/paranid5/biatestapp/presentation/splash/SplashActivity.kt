package com.paranid5.biatestapp.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.paranid5.biatestapp.presentation.auth.AuthActivity
import com.paranid5.biatestapp.presentation.ui.theme.BIATestAppTheme
import kotlinx.coroutines.delay

private const val SPLASH_SCREEN_DELAY_MS = 2000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BIATestAppTheme {
                LaunchedEffect(Unit) {
                    delay(SPLASH_SCREEN_DELAY_MS)
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                }

                SplashScreen(Modifier.fillMaxSize())
            }
        }
    }
}