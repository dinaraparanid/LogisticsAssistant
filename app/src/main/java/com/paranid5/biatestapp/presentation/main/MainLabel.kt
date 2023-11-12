package com.paranid5.biatestapp.presentation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.paranid5.biatestapp.presentation.main.chat.ChatLabel
import com.paranid5.biatestapp.presentation.main.profile.ProfileLabel
import com.paranid5.biatestapp.presentation.main.schedules.SchedulesLabel
import com.paranid5.biatestapp.presentation.main.tasks.TasksLabel
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel
import com.paranid5.biatestapp.presentation.main.tasks.selected_task.SelectedTaskLabel

@Composable
fun MainLabel(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val navController = LocalMainNavController.current
    val curScreen by navController.currentScreenState.collectAsState()

    when (curScreen) {
        MainNavController.TASKS_SCREEN ->
            TasksLabel(modifier.fillMaxWidth())

        MainNavController.SCHEDULES_SCREEN ->
            SchedulesLabel(modifier.fillMaxWidth())

        MainNavController.CHAT_SCREEN ->
            ChatLabel(modifier.fillMaxWidth())

        MainNavController.PROFILE_SCREEN ->
            ProfileLabel(modifier.fillMaxWidth())

        MainNavController.SELECTED_TASK_SCREEN ->
            SelectedTaskLabel(
                tasksViewModel = tasksViewModel,
                modifier = modifier.fillMaxWidth()
            )
    }
}