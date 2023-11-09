package com.paranid5.biatestapp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.paranid5.biatestapp.presentation.tasks.TasksLabel
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Composable
fun MainLabel(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current
    val curScreen by navController.currentScreenState.collectAsState()

    when (curScreen) {
        MainNavController.TASKS_SCREEN -> {
            TasksLabel(modifier.background(colors.background))
        }

        MainNavController.GRAPHS_SCREEN -> {

        }

        MainNavController.CHAT_SCREEN -> {

        }

        MainNavController.PROFILE_SCREEN -> {

        }
    }
}