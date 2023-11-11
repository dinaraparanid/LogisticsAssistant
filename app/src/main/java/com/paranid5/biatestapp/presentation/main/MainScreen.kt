package com.paranid5.biatestapp.presentation.main

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paranid5.biatestapp.presentation.app_bar.MainAppBar
import com.paranid5.biatestapp.presentation.main.chat.ChatScreen
import com.paranid5.biatestapp.presentation.main.chat.ChatViewModel
import com.paranid5.biatestapp.presentation.main.chat.LocalNewMessagesAmountShown
import com.paranid5.biatestapp.presentation.main.chat.LocalNewMessageNotDisposed
import com.paranid5.biatestapp.presentation.main.profile.ProfileScreen
import com.paranid5.biatestapp.presentation.main.schedules.SchedulesScreen
import com.paranid5.biatestapp.presentation.main.tasks.TasksScreen
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel()
) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current

    val unreadMessages by chatViewModel.unreadMessagesState.collectAsState()

    val unreadMessagesAmount by remember {
        derivedStateOf { unreadMessages.size }
    }

    DisposableEffect(Unit) {
        val task = chatViewModel.viewModelScope.launch {
            chatViewModel.loadEmployer()

            while (true) {
                chatViewModel.loadMessagesFromNetwork()
                delay(1000)
            }
        }

        onDispose { task.cancel() }
    }

    CompositionLocalProvider(
        LocalNewMessageNotDisposed provides MutableStateFlow(true),
        LocalNewMessagesAmountShown provides MutableStateFlow(0)
    ) {
        val newMessageNotDisposedState = LocalNewMessageNotDisposed.current
        val newMessagesAmountShownState = LocalNewMessagesAmountShown.current
        val isNewMessageNotDisposed by newMessageNotDisposedState.collectAsState()

        LaunchedEffect(unreadMessagesAmount, isNewMessageNotDisposed) {
            if (isNewMessageNotDisposed)
                newMessagesAmountShownState.update { unreadMessagesAmount }
        }

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
                    newMessageNotDisposedState.update { true }
                    TasksScreen(Modifier.fillMaxSize())
                }

                composable(route = MainNavController.SCHEDULES_SCREEN) {
                    newMessageNotDisposedState.update { true }
                    SchedulesScreen(Modifier.fillMaxSize())
                }

                composable(route = MainNavController.CHAT_SCREEN) {
                    newMessageNotDisposedState.update { false }

                    ChatScreen(
                        chatViewModel = chatViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = MainNavController.PROFILE_SCREEN) {
                    newMessageNotDisposedState.update { true }
                    ProfileScreen(Modifier.fillMaxSize())
                }
            }
        }
    }
}