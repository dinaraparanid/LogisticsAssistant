package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun FullDescriptionItem(
    title: String,
    data: String,
    modifier: Modifier = Modifier,
    dataColor: Color? = null
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Text(
            text = title,
            color = colors.onPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = data,
            color = dataColor ?: colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    }
}