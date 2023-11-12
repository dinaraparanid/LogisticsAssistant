package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun SelectedTaskLabel(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val selectedTask by tasksViewModel.selectedTaskState.collectAsState()

    Box(modifier.background(colors.background)) {
        Text(
            text = selectedTask!!.title,
            fontSize = 22.sp,
            color = colors.primary,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)
        )
    }
}