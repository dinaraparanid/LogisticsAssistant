package com.paranid5.biatestapp.presentation.app_bar

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.LocalMainNavController
import com.paranid5.biatestapp.presentation.main.MainNavController
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Composable
fun MainAppBar(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current

    BottomAppBar(
        modifier = modifier.height(appBarHeight),
        containerColor = colors.background
    ) {
        MainAppBarItem(
            screenKey = MainNavController.TASKS_SCREEN,
            iconPainter = painterResource(id = R.drawable.tasks_icon),
            description = stringResource(id = R.string.tasks),
            onItemClicked = { navController.navigateToTasksScreen() },
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 8.dp)
        )

        MainAppBarItem(
            screenKey = MainNavController.GRAPHS_SCREEN,
            iconPainter = painterResource(id = R.drawable.graphs_icon),
            description = stringResource(id = R.string.graphs),
            onItemClicked = { navController.navigateToGraphsScreen() },
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 8.dp)
        )

        MainAppBarItem(
            screenKey = MainNavController.CHAT_SCREEN,
            iconPainter = painterResource(id = R.drawable.chat_icon),
            description = stringResource(id = R.string.tasks),
            onItemClicked = { navController.navigateToChatScreen() },
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 8.dp)
        )

        MainAppBarItem(
            screenKey = MainNavController.PROFILE_SCREEN,
            description = stringResource(id = R.string.profile),
            onItemClicked = { navController.navigateToProfileScreen() },
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 8.dp),
            iconView = { contentColor, descr, iconModifier ->
                Icon(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = descr,
                    tint = contentColor,
                    modifier = iconModifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        )
    }
}

private inline val appBarHeight
    @Composable
    get() = when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 64.dp
        else -> 88.dp
    }