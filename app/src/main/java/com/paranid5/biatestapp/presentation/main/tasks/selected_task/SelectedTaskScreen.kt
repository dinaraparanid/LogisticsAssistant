package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel

@Composable
fun SelectedTaskScreen(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(modifier.verticalScroll(scrollState)) {
        FullTaskInfo(
            tasksViewModel = tasksViewModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        Route(
            tasksViewModel = tasksViewModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}
