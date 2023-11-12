package com.paranid5.biatestapp.presentation.main.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.paranid5.biatestapp.data.retrofit.tasks.Task
import com.paranid5.biatestapp.domain.BiaLogisticsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val savesStateHandle: SavedStateHandle,
    private val logisticsClient: BiaLogisticsClient,
) : ViewModel() {
    companion object {
        const val INCOMING_TASKS = "incoming_tasks"
        const val EXECUTING_TASKS = "executing_tasks"
        const val SELECTED_TASK = "selected_task"
    }

    private val _incomingTasksState = MutableStateFlow(listOf<Task>())

    val incomingTasks = _incomingTasksState.asStateFlow()

    private fun setIncomingTasks(tasks: List<Task>) {
        savesStateHandle[INCOMING_TASKS] = _incomingTasksState.updateAndGet { tasks }
    }

    private val _executingTasksState = MutableStateFlow(listOf<Task>())

    val executingTasks = _executingTasksState.asStateFlow()

    private fun setExecutingTasks(tasks: List<Task>) {
        savesStateHandle[EXECUTING_TASKS] = _executingTasksState.updateAndGet { tasks }
    }

    private val _selectedTaskState = MutableStateFlow<Task?>(null)

    val selectedTaskState = _selectedTaskState.asStateFlow()

    fun setSelectedTask(task: Task?) {
        savesStateHandle[SELECTED_TASK] = _selectedTaskState.updateAndGet { task }
    }

    suspend fun loadIncomingTasks() {
        val tasks = runCatching {
            logisticsClient.getIncomingTasks().body()
        }.getOrNull() ?: incomingTasksPlaceholder

        setIncomingTasks(tasks)
    }

    suspend fun loadExecutingTasks() {
        val tasks = runCatching {
            logisticsClient.getExecutingTasks().body()
        }.getOrNull() ?: executingTasksPlaceholder

        setExecutingTasks(tasks)
    }
}