package com.paranid5.biatestapp.presentation.main

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paranid5.biatestapp.presentation.app_bar.MainAppBar
import com.paranid5.biatestapp.presentation.chat.ChatScreen
import com.paranid5.biatestapp.presentation.graphs.GraphsScreen
import com.paranid5.biatestapp.presentation.profile.ProfileScreen
import com.paranid5.biatestapp.presentation.tasks.TasksScreen
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current

    Scaffold(
        modifier = modifier,
        containerColor = colors.onBackground,
        topBar = { MainLabel() },
        bottomBar = { MainAppBar() }
    ) { padding ->
        NavHost(
            navController = navController.navController!!,
            startDestination = MainNavController.INITIAL_SCREEN,
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                end = padding.calculateEndPadding(LayoutDirection.Ltr)
            )
        ) {
            composable(route = MainNavController.TASKS_SCREEN) {
                TasksScreen(Modifier.fillMaxSize())
            }

            composable(route = MainNavController.GRAPHS_SCREEN) {
                GraphsScreen(Modifier.fillMaxSize())
            }

            composable(route = MainNavController.CHAT_SCREEN) {
                ChatScreen(Modifier.fillMaxSize())
            }

            composable(route = MainNavController.PROFILE_SCREEN) {
                ProfileScreen(Modifier.fillMaxSize())
            }
        }
    }
}