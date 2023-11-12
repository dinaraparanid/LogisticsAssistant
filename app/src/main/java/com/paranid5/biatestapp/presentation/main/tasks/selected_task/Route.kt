package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.data.retrofit.tasks.Destination
import com.paranid5.biatestapp.presentation.main.tasks.TaskStatusLabel
import com.paranid5.biatestapp.presentation.main.tasks.TasksViewModel
import com.paranid5.biatestapp.presentation.ui.theme.BlueLink
import com.paranid5.biatestapp.presentation.ui.theme.DarkGray
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.theme.UnloadBackground
import com.paranid5.biatestapp.presentation.ui.utils.Divider
import com.paranid5.biatestapp.presentation.ui.utils.ext.toTimeStringFormat

@Composable
fun Route(
    tasksViewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val task by tasksViewModel.selectedTaskState.collectAsState()

    val route by remember {
        derivedStateOf { task?.destinationPoints }
    }

    Card(
        modifier = modifier.animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background)
    ) {
        Text(
            text = stringResource(id = R.string.route),
            color = colors.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            route?.forEachIndexed { i, dest ->
                DestinationView(
                    index = i,
                    destination = dest,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                if (i + 1 != route?.size) {
                    Divider()
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun DestinationView(
    index: Int,
    destination: Destination,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val isOpenedState = remember { mutableStateOf(false) }
    val isOpened by isOpenedState

    val backgroundColor = when (destination.taskType) {
        "Погрузка" -> colors.primary
        else -> UnloadBackground
    }

    val textColor = when (destination.taskType) {
        "Погрузка" -> colors.background
        else -> DarkGray

    }

    Column(modifier) {
        DropDownRow(
            label = "${index + 1} ${stringResource(id = R.string.point)}",
            isOpenedState = isOpenedState,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        ) {
            TaskStatusLabel(
                label = destination.taskType,
                backgroundColor = backgroundColor,
                textColor = textColor,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        if (isOpened)
            DetailedDescription(
                destination = destination,
                modifier = Modifier.fillMaxWidth()
            )
    }
}

@Composable
private fun DetailedDescription(
    destination: Destination,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        FullDescriptionItem(
            title = stringResource(id = R.string.start_location),
            data = destination.startLocation,
            dataColor = BlueLink
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.loading_time),
            data = destination.loadingTime.toTimeStringFormat()
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.company),
            data = destination.company
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.contact_person),
            color = colors.onPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = destination.contact.contactPerson,
            color = colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = destination.contact.phoneNumber,
            color = BlueLink,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(12.dp))

        FullDescriptionItem(
            title = stringResource(id = R.string.commentary),
            data = destination.commentary
        )
    }
}