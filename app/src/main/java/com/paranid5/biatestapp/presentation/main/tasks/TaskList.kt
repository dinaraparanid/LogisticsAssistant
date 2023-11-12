package com.paranid5.biatestapp.presentation.main.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.biatestapp.data.retrofit.tasks.Task
import com.paranid5.biatestapp.presentation.main.LocalMainNavController
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Composable
fun TaskList(
    tasks: List<Task>,
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) = LazyColumn(modifier) {
    item { Spacer(Modifier.height(16.dp)) }

    items(tasks) {
        TaskView(
            task = it,
            tasksViewModel = tasksViewModel,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun TaskView(
    task: Task,
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val navController = LocalMainNavController.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background),
        modifier = modifier.clickable {
            tasksViewModel.setSelectedTask(task)
            navController.navigateToSelectedTaskScreen()
        }
    ) {
        TaskShortDescription(
            task = task,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        TaskShortDestinations(
            destinations = task.destinationPoints,
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}