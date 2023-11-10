package com.paranid5.biatestapp.presentation.main.chat

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.data.Message
import com.paranid5.biatestapp.data.utils.ext.toDateFormatString
import com.paranid5.biatestapp.data.utils.ext.toTimeFormatString
import com.paranid5.biatestapp.presentation.main.LocalEmployee
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.MiddleGray
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.SortedMap
import java.util.SortedSet

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val messagesState = remember {
        mutableStateOf<List<Message>>(listOf())
    }

    DisposableEffect(Unit) {
        val task = chatViewModel.viewModelScope.launch {
            chatViewModel.loadEmployer()

            while (true) {
                messagesState.value = chatViewModel.loadMessages()
                delay(1000)
            }
        }

        onDispose { task.cancel() }
    }

    Box(modifier) {
        MessagesList(
            messagesState = messagesState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun MessagesList(
    messagesState: State<List<Message>>,
    modifier: Modifier = Modifier
) {
    val messages by messagesState
    val messagesMap = associateMessagesToDates(messages)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        var newMessagesShown = false

        messagesMap.forEach { (date, msgs) ->
            item {
                DateLabel(date = date, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
            }

            items(msgs.toList()) {
                if (!it.read && !newMessagesShown) {
                    newMessagesShown = true
                    NewMessagesLabel(Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                }

                MessageView(message = it)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun DateLabel(date: LocalDate, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Text(
        text = date.toDateFormatString(context),
        color = MiddleGray,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        fontFamily = StolzlFontFamily,
        modifier = modifier
    )
}

@Composable
private fun NewMessagesLabel(modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()
    val text = stringResource(id = R.string.new_messages)

    val textLayout = textMeasurer.measure(
        text = text,
        style = TextStyle(
            color = MiddleGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily
        ),
    )

    val textWidth = textLayout.size.width
    val textHeight = textLayout.size.height

    Canvas(modifier = modifier.height(textHeight.pxToDp())) {
        val lineWidth = (size.width - textWidth) / 2 - 8

        drawLine(
            color = MiddleGray,
            start = Offset(0F, center.y),
            end = Offset(lineWidth, center.y)
        )

        drawText(
            textLayoutResult = textLayout,
            color = MiddleGray,
            topLeft = Offset(lineWidth + 8, 0F)
        )

        drawLine(
            color = MiddleGray,
            start = Offset(size.width - lineWidth, center.y),
            end = Offset(size.width, center.y)
        )
    }
}

@Composable
private fun MessageView(message: Message, modifier: Modifier = Modifier) {
    val employee = LocalEmployee.current

    val isMessageFromEmployee by remember {
        derivedStateOf { message.fromUserId == employee.employeeId }
    }

    Box(modifier.fillMaxWidth()) {
        when {
            isMessageFromEmployee -> EmployeeMessageView(message = message, modifier = modifier)
            else -> EmployerMessageView(message = message, modifier = modifier)
        }
    }
}

context(BoxScope)
@Composable
private fun EmployeeMessageView(message: Message, modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val textColor = colors.primary
    val backgroundColor = colors.background

    val timeMeasurer = rememberTextMeasurer()

    val timeLayout = timeMeasurer.measure(
        text = message.sendTime.time.toTimeFormatString(),
        style = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    )

    Card(
        modifier = modifier.align(Alignment.CenterEnd),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Text(
            text = message.text,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    top = 8.dp,
                    start = 12.dp,
                    end = 24.dp + timeLayout.size.width.pxToDp()
                )
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = message.sendTime.time.toTimeFormatString(),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 8.dp, end = 12.dp)
        )
    }
}

context(BoxScope)
@Composable
private fun EmployerMessageView(message: Message, modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val textColor = colors.background
    val backgroundColor = colors.primary

    Card(
        modifier = modifier.align(Alignment.CenterStart),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Text(
            text = message.text,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 8.dp, start = 12.dp, end = 12.dp)
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = message.sendTime.time.toTimeFormatString(),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp, start = 12.dp)
        )
    }
}

private fun associateMessagesToDates(messages: List<Message>): SortedMap<LocalDate, SortedSet<Message>> {
    val messagesMap = sortedMapOf<LocalDate, SortedSet<Message>>()

    messages
        .map { it.sendTime.date to it }
        .forEach { (date, message) ->
            val msgs = messagesMap[date] ?: sortedSetOf()
            msgs.add(message)
            messagesMap[date] = msgs
        }

    return messagesMap
}