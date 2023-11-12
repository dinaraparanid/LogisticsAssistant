package com.paranid5.biatestapp.presentation.main.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.data.retrofit.tasks.Task
import com.paranid5.biatestapp.presentation.ui.theme.BlueBackground
import com.paranid5.biatestapp.presentation.ui.theme.BlueText
import com.paranid5.biatestapp.presentation.ui.theme.DarkGray
import com.paranid5.biatestapp.presentation.ui.theme.GreenBackground
import com.paranid5.biatestapp.presentation.ui.theme.GreenText
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.OrangeBackground
import com.paranid5.biatestapp.presentation.ui.theme.OrangeText
import com.paranid5.biatestapp.presentation.ui.theme.PurpleBackground
import com.paranid5.biatestapp.presentation.ui.theme.PurpleText
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.toTimeStringFormat

@Composable
fun TaskShortDescription(
    task: Task,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = task.title,
                color = colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = task.price,
                color = colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(Modifier.heightIn(4.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = task.orderDateTime.toTimeStringFormat(),
                color = DarkGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )

            Spacer(Modifier.width(8.dp))

            TaskStatusLabel(
                taskStatus = task.status,
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun TaskStatusLabel(
    taskStatus: String,
    modifier: Modifier = Modifier
) = Box(modifier) {
    when (taskStatus) {
        "Новое" -> NewLabel(modifier = Modifier.align(Alignment.CenterEnd))
        "Запланированные" -> PlannedLabel(modifier = Modifier.align(Alignment.CenterEnd))
        "В процессе" -> InProcessLabel(modifier = Modifier.align(Alignment.CenterEnd))
        "Проверка" -> CheckingLabel(modifier = Modifier.align(Alignment.CenterEnd))
        else -> throw IllegalArgumentException("Unknown task status")
    }
}

@Composable
private fun NewLabel(modifier: Modifier = Modifier) =
    TaskStatusLabel(
        label = stringResource(id = R.string.new_label),
        backgroundColor = GreenBackground,
        textColor = GreenText,
        modifier = modifier
    )

@Composable
private fun PlannedLabel(modifier: Modifier = Modifier) =
    TaskStatusLabel(
        label = stringResource(id = R.string.planned_label),
        backgroundColor = BlueBackground,
        textColor = BlueText,
        modifier = modifier
    )

@Composable
private fun InProcessLabel(modifier: Modifier = Modifier) =
    TaskStatusLabel(
        label = stringResource(id = R.string.in_process_label),
        backgroundColor = PurpleBackground,
        textColor = PurpleText,
        modifier = modifier
    )

@Composable
private fun CheckingLabel(modifier: Modifier = Modifier) =
    TaskStatusLabel(
        label = stringResource(id = R.string.checking_label),
        backgroundColor = OrangeBackground,
        textColor = OrangeText,
        modifier = modifier
    )