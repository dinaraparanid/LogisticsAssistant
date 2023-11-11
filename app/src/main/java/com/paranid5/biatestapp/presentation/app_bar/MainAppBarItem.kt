package com.paranid5.biatestapp.presentation.app_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.presentation.main.LocalMainNavController
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

typealias IconView = @Composable BoxScope.(
    contentColor: Color,
    descr: String,
    iconModifier: Modifier
) -> Unit

@Composable
fun MainAppBarItem(
    screenKey: String,
    iconView: IconView,
    description: String,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current
    val currentScreen by navController.currentScreenState.collectAsState()

    val isCurrentScreen by remember {
        derivedStateOf { currentScreen == screenKey }
    }

    val cardColor by remember {
        derivedStateOf { if (isCurrentScreen) colors.onBackground else Color.Transparent }
    }

    val contentColor by remember {
        derivedStateOf { if (isCurrentScreen) colors.primary else Color.LightGray }
    }

    Column(modifier.clickable { onItemClicked() }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            content = {
               Box(Modifier.fillMaxWidth()) {
                   iconView(
                       contentColor,
                       description,
                       Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                   )
               }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp),
        )

        Text(
            text = description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            textAlign = TextAlign.Center,
            color = contentColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MainAppBarItem(
    screenKey: String,
    iconPainter: Painter,
    description: String,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) = MainAppBarItem(
    screenKey = screenKey,
    iconView = { contentColor, descr, iconModifier ->
        Icon(
            painter = iconPainter,
            contentDescription = descr,
            tint = contentColor,
            modifier = iconModifier
                .size(24.dp)
                .align(Alignment.Center)
        )
    },
    description = description,
    onItemClicked = onItemClicked,
    modifier = modifier
)