package com.paranid5.biatestapp.presentation.main

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paranid5.biatestapp.presentation.main.app_bar.MainAppBar
import com.paranid5.biatestapp.presentation.main.chat.ChatScreen
import com.paranid5.biatestapp.presentation.main.chat.ChatViewModel
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessageNotDisposed
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessagesAmountShown
import com.paranid5.biatestapp.presentation.main.profile.ProfileScreen
import com.paranid5.biatestapp.presentation.main.schedules.SchedulesScreen
import com.paranid5.biatestapp.presentation.main.tasks.TasksScreen
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel
import com.paranid5.biatestapp.presentation.main.tasks.selected_task.SelectedTaskScreen
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel(),
    tasksViewModel: TasksViewModel = viewModel()
) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current

    val newMessageNotDisposedState = LocalNewMessageNotDisposed.current
    val newMessagesAmountShownState = LocalNewMessagesAmountShown.current
    val isNewMessageNotDisposed by newMessageNotDisposedState.collectAsState()

    var isLocalAppBarVisible by remember {
        mutableStateOf(true)
    }

    val appBarHeight by animateIntAsState(
        targetValue = if (isLocalAppBarVisible) 80 else 0,
        label = ""
    )

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

    LaunchedEffect(unreadMessagesAmount, isNewMessageNotDisposed) {
        if (isNewMessageNotDisposed)
            newMessagesAmountShownState.update { unreadMessagesAmount }
    }

    Scaffold(
        modifier = modifier,
        containerColor = colors.onBackground,
        topBar = { MainLabel(tasksViewModel) },
        bottomBar = { MainAppBar(Modifier.height(appBarHeight.dp)) }
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
                isLocalAppBarVisible = true
                newMessageNotDisposedState.update { true }
                navController.resetCurrentScreenStateToTasks()

                TasksScreen(
                    tasksViewModel = tasksViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = MainNavController.SCHEDULES_SCREEN) {
                isLocalAppBarVisible = true
                newMessageNotDisposedState.update { true }
                navController.resetCurrentScreenStateToSchedules()
                SchedulesScreen(Modifier.fillMaxSize())
            }

            composable(route = MainNavController.CHAT_SCREEN) {
                isLocalAppBarVisible = true
                newMessageNotDisposedState.update { false }
                navController.resetCurrentScreenStateToChat()

                ChatScreen(
                    chatViewModel = chatViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = MainNavController.PROFILE_SCREEN) {
                isLocalAppBarVisible = true
                newMessageNotDisposedState.update { true }
                navController.resetCurrentScreenStateToProfile()
                ProfileScreen(Modifier.fillMaxSize())
            }

            composable(route = MainNavController.SELECTED_TASK_SCREEN) {
                isLocalAppBarVisible = false
                newMessageNotDisposedState.update { true }
                navController.resetCurrentScreenStateToSelectedTask()

                SelectedTaskScreen(
                    tasksViewModel = tasksViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}