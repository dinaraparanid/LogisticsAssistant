package com.paranid5.biatestapp.presentation.app_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.LocalMainNavController
import com.paranid5.biatestapp.presentation.main.MainNavController
import com.paranid5.biatestapp.presentation.main.chat.LocalNewMessageNotDisposed
import com.paranid5.biatestapp.presentation.main.chat.LocalNewMessagesAmountShown
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.Red
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun MainAppBar(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current
    val newMessagesAmountShown by LocalNewMessagesAmountShown.current.collectAsState()

    val isUnreadMessagesVisible by remember {
        derivedStateOf { newMessagesAmountShown > 0 }
    }

    BottomAppBar(
        modifier = modifier,
        containerColor = colors.background
    ) {
        MainAppBarItem(
            screenKey = MainNavController.TASKS_SCREEN,
            iconPainter = painterResource(id = R.drawable.tasks_icon),
            description = stringResource(id = R.string.tasks),
            onItemClicked = { navController.navigateToTasksScreen() },
            modifier = Modifier
                .weight(1F)
                .padding(top = 4.dp, bottom = 8.dp, start = 6.dp, end = 8.dp)
        )

        MainAppBarItem(
            screenKey = MainNavController.SCHEDULES_SCREEN,
            iconPainter = painterResource(id = R.drawable.schedules_icon),
            description = stringResource(id = R.string.schedules),
            onItemClicked = { navController.navigateToSchedulesScreen() },
            modifier = Modifier
                .weight(1F)
                .padding(top = 4.dp, bottom = 8.dp, start = 6.dp, end = 8.dp)
        )

        MainAppBarItem(
            screenKey = MainNavController.CHAT_SCREEN,
            description = stringResource(id = R.string.chat),
            onItemClicked = { navController.navigateToChatScreen() },
            modifier = Modifier
                .weight(1F)
                .padding(top = 4.dp, bottom = 8.dp, start = 6.dp, end = 8.dp),
            iconView = { contentColor, descr, iconModifier ->
                if (isUnreadMessagesVisible)
                    Card(
                        shape = RoundedCornerShape(100.dp),
                        colors = CardDefaults.cardColors(containerColor = Red),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 9.dp)
                            .zIndex(10F),
                    ) {
                        Text(
                            text = newMessagesAmountShown.toString(),
                            color = colors.background,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = StolzlFontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 1.dp, horizontal = 5.dp)
                        )
                    }

                Icon(
                    painter = painterResource(id = R.drawable.chat_icon),
                    contentDescription = descr,
                    tint = contentColor,
                    modifier = iconModifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        )

        MainAppBarItem(
            screenKey = MainNavController.PROFILE_SCREEN,
            iconPainter = painterResource(id = R.drawable.profile_icon),
            description = stringResource(id = R.string.profile),
            onItemClicked = { navController.navigateToProfileScreen() },
            modifier = Modifier
                .weight(1F)
                .padding(top = 4.dp, bottom = 8.dp, start = 6.dp, end = 8.dp)
        )
    }
}