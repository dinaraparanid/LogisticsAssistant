package com.paranid5.biatestapp.presentation.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun TasksLabel(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value

    Box(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.tasks),
            fontSize = 22.sp,
            color = colors.primary,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    }
}