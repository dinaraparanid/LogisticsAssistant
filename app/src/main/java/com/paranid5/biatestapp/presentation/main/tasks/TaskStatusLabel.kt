package com.paranid5.biatestapp.presentation.main.tasks

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun TaskStatusLabel(
    label: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) = Card(
    modifier = modifier,
    shape = RoundedCornerShape(4.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
) {
    Text(
        text = label,
        color = textColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = StolzlFontFamily,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    )
}