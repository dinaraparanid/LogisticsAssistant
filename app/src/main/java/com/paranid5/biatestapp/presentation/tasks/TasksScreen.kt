package com.paranid5.biatestapp.presentation.tasks

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabIndex = pagerState.currentPage

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
                text = stringResource(id = R.string.in_process),
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp),
            )
        }

        HorizontalPager(state = pagerState, modifier = modifier) { page ->
            when (page) {
                0 -> TaskList(modifier = Modifier.fillMaxSize())
                1 -> TaskList(modifier = Modifier.fillMaxSize())
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