package com.paranid5.biatestapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R

val StolzlFontFamily = FontFamily(
    Font(R.font.stolzl_bold, FontWeight.Bold),
    Font(R.font.stolzl_light, FontWeight.Light),
    Font(R.font.stolzl_medium, FontWeight.Medium),
    Font(R.font.stolzl_regular, FontWeight.Normal),
    Font(R.font.stolzl_thin, FontWeight.Thin)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = StolzlFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)