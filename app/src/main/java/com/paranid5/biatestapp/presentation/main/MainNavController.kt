package com.paranid5.biatestapp.presentation.main

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet

class MainNavController(val navController: NavHostController? = null) {
    companion object {
        const val TASKS_SCREEN = "tasks"
        const val GRAPHS_SCREEN = "graphs"
        const val CHAT_SCREEN = "chat"
        const val PROFILE_SCREEN = "profile"
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

    fun navigateToGraphsScreen() = navigateToScreenIfNotSame(GRAPHS_SCREEN)

    fun navigateToChatScreen() = navigateToScreenIfNotSame(CHAT_SCREEN)

    fun navigateToProfileScreen() = navigateToScreenIfNotSame(PROFILE_SCREEN)
}

val LocalMainNavController = staticCompositionLocalOf { MainNavController() }