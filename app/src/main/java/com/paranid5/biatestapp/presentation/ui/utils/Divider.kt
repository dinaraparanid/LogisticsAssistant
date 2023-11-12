package com.paranid5.biatestapp.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Composable
fun Divider(modifier: Modifier = Modifier, color: Color? = null) {
    val colors = LocalAppColors.current.value

    Spacer(
        modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color ?: colors.onBackground)
    )
}

@Composable
fun Divider(
    width: Dp,
    modifier: Modifier = Modifier,
    color: Color? = null,
) {
    val colors = LocalAppColors.current.value

    Spacer(
        modifier
            .height(1.dp)
            .width(width)
            .background(color ?: colors.onBackground)
    )
}