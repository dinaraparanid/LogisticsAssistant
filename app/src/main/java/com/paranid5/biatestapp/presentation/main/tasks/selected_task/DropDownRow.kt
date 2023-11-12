package com.paranid5.biatestapp.presentation.main.tasks.selected_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun DropDownRow(
    label: String,
    isOpenedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    decoration: @Composable () -> Unit = { Spacer(Modifier.width(12.dp)) },
) {
    val colors = LocalAppColors.current.value
    var isOpened by isOpenedState

    Row(modifier.clickable { isOpened = !isOpened }) {
        Text(
            text = label,
            color = colors.primary,
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically)
        )

        decoration()

        DropDownIcon(
            isOpenedState = isOpenedState,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun DropDownIcon(
    isOpenedState: State<Boolean>,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val isOpened by isOpenedState

    Box(modifier) {
        when {
            isOpened -> Icon(
                painter = painterResource(id = R.drawable.arrow_drop_down_line),
                contentDescription = stringResource(id = R.string.task_information),
                tint = colors.primary,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )

            else -> Icon(
                painter = painterResource(id = R.drawable.arrow_drop_up_line),
                contentDescription = stringResource(id = R.string.task_information),
                tint = colors.primary,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
        }
    }
}