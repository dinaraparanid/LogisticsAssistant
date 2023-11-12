package com.paranid5.biatestapp.presentation.main.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabIndex = pagerState.currentPage

    val incomingTasks by tasksViewModel.incomingTasks.collectAsState()
    val executingTasks by tasksViewModel.executingTasks.collectAsState()

    DisposableEffect(Unit) {
        val task = tasksViewModel.viewModelScope.launch {
            while (true) {
                tasksViewModel.loadIncomingTasks()
                tasksViewModel.loadExecutingTasks()
                delay(1000)
            }
        }

        onDispose { task.cancel() }
    }

    Column(modifier) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colors.background)
        ) {
            TabItem(
                tabIndex = 0,
                text = stringResource(id = R.string.incoming),
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp),
            )

            TabItem(
                tabIndex = 1,
                text = stringResource(id = R.string.executing),
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp),
            )
        }

        HorizontalPager(state = pagerState, modifier = modifier) { page ->
            when (page) {
                0 -> TaskList(
                    tasks = incomingTasks,
                    tasksViewModel = tasksViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp)
                )

                1 -> TaskList(
                    tasks = executingTasks,
                    tasksViewModel = tasksViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabItem(
    tabIndex: Int,
    text: String,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val coroutineScope = rememberCoroutineScope()

    val curTabIndex = pagerState.currentPage
    val isSelected = curTabIndex == tabIndex
    val textColor = if (isSelected) colors.primary else Color.LightGray

    Tab(
        modifier = modifier,
        selected = isSelected,
        onClick = {
            coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) }
        }
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}