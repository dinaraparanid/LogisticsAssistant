package com.paranid5.biatestapp.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = LightGray,
    secondary = Red,
    background = Color.Black,
    onBackground = DarkGray
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = DarkGray,
    secondary = Red,
    background = Color.White,
    onBackground = LightGray
)

@JvmInline
value class AppColors(val value: ColorScheme = LightColorScheme)

val LocalAppColors = staticCompositionLocalOf { AppColors() }

@Composable
fun BIATestAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // При желании, можно задать системную тему
//    val appColors = AppColors(
//        when {
//            darkTheme -> DarkColorScheme
//            else -> LightColorScheme
//        }
//    )

    val appColors = AppColors(LightColorScheme)

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = appColors.value.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = appColors.value,
            typography = Typography,
            content = content
        )
    }
}