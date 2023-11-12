package com.paranid5.biatestapp.presentation.main

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

class MainNavController(val navController: NavHostController? = null) {
    companion object {
        const val TASKS_SCREEN = "tasks"
        const val SCHEDULES_SCREEN = "schedules"
        const val CHAT_SCREEN = "chat"
        const val PROFILE_SCREEN = "profile"
        const val SELECTED_TASK_SCREEN = "tasks/selected_task"
        const val INITIAL_SCREEN = TASKS_SCREEN
    }

    private val _currentScreenState = MutableStateFlow(INITIAL_SCREEN)

    val currentScreenState = _currentScreenState.asStateFlow()

    private fun navigateToScreen(route: String) {
        navController?.navigate(_currentScreenState.updateAndGet { route })
            ?: throw IllegalStateException("MainNavController was not initialized")
    }

    private fun navigateToScreenIfNotSame(route: String) {
        if (route != _currentScreenState.value)
            navigateToScreen(route)
    }

    fun navigateToTasksScreen() = navigateToScreenIfNotSame(TASKS_SCREEN)

    fun navigateToSchedulesScreen() = navigateToScreenIfNotSame(SCHEDULES_SCREEN)

    fun navigateToChatScreen() = navigateToScreenIfNotSame(CHAT_SCREEN)

    fun navigateToProfileScreen() = navigateToScreenIfNotSame(PROFILE_SCREEN)

    fun navigateToSelectedTaskScreen() = navigateToScreenIfNotSame(SELECTED_TASK_SCREEN)

    private fun resetCurrentScreenState(screen: String) =
        _currentScreenState.update { screen }

    fun resetCurrentScreenStateToTasks() =
        resetCurrentScreenState(TASKS_SCREEN)

    fun resetCurrentScreenStateToSchedules() =
        resetCurrentScreenState(SCHEDULES_SCREEN)

    fun resetCurrentScreenStateToChat() =
        resetCurrentScreenState(CHAT_SCREEN)

    fun resetCurrentScreenStateToProfile() =
        resetCurrentScreenState(PROFILE_SCREEN)

    fun resetCurrentScreenStateToSelectedTask() =
        resetCurrentScreenState(SELECTED_TASK_SCREEN)
}

val LocalMainNavController = staticCompositionLocalOf { MainNavController() }