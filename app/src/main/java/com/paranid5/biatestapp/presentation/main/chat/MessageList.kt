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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.data.Message
import com.paranid5.biatestapp.data.room.chat.DBMessage
import com.paranid5.biatestapp.data.utils.ext.toDateFormatString
import com.paranid5.biatestapp.data.utils.ext.toTimeFormatString
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalEmployee
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessageNotDisposed
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessagesAmountShown
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.MiddleGray
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.pxToDp
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import java.util.SortedMap
import java.util.SortedSet

@Composable
fun MessagesList(
    itemsInListState: MutableState<Int>,
    listState: LazyListState,
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    // ------------------- Messages Presentation -------------------

    val messages by chatViewModel.messagesState.collectAsState()
    val messagesMap = associateMessagesToDates(messages)
    val sortedMessages = messages.sorted()

    val curVisibleItemIndex by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    var lastVisibleItemIndex by remember {
        mutableIntStateOf(curVisibleItemIndex)
    }

    // ------------------- Unread Messages -------------------

    val newMessageNotDisposedState = LocalNewMessageNotDisposed.current
    val newMessagesAmountShownState = LocalNewMessagesAmountShown.current

    val isNewMessageDisposed by newMessageNotDisposedState.collectAsState()
    val newMessagesAmountShown by newMessagesAmountShownState.collectAsState()
    val unreadMessages by chatViewModel.unreadMessagesState.collectAsState()

    var newMessagesShown by remember {
        mutableStateOf(false)
    }

    var newMessageIndex by remember {
        mutableIntStateOf(-1)
    }

    var newMessageId by remember {
        mutableIntStateOf(-1)
    }

    val unreadMessagesAmount by remember {
        derivedStateOf { unreadMessages.size }
    }

    // -----------------------------------------------------------

    LaunchedEffect(newMessageIndex) {
        if (newMessageIndex != -1)
            listState.scrollToItem(newMessageIndex)
    }

    LaunchedEffect(curVisibleItemIndex) {
        lastVisibleItemIndex = maxOf(curVisibleItemIndex, lastVisibleItemIndex)

        chatViewModel.readMessages(
            *sortedMessages
                .take(lastVisibleItemIndex + 1)
                .takeLastWhile { !it.read }
                .onEach { println(it) }
                .toTypedArray()
        )
    }

    LaunchedEffect(newMessageId) {
        newMessageNotDisposedState.update { newMessageId > 0 }
    }

    LaunchedEffect(unreadMessagesAmount, isNewMessageDisposed) {
        if (isNewMessageDisposed)
            newMessagesAmountShownState.update { unreadMessagesAmount }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        var newMessagesCnt = 0
        itemsInListState.value = 0

        messagesMap.forEach { (date, msgs) ->
            item {
                DateLabel(date = date, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                ++itemsInListState.value
            }

            itemsInListState.value += msgs.size

            items(msgs.toList()) {
                if ((!it.read && !newMessagesShown) || newMessageId == it.id) {
                    newMessagesShown = true
                    newMessageIndex = newMessagesCnt
                    newMessageId = it.id

                    if (newMessagesAmountShown > 0) {
                        NewMessagesLabel(Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    ++itemsInListState.value
                }

                MessageView(message = it)
                Spacer(Modifier.height(12.dp))
                ++newMessagesCnt
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

private fun associateMessagesToDates(
    messages: List<DBMessage>
): SortedMap<LocalDate, SortedSet<DBMessage>> {
    val messagesMap = sortedMapOf<LocalDate, SortedSet<DBMessage>>()

    messages
        .map { it.sendTime.date to it }
        .forEach { (date, message) ->
            val msgs = messagesMap[date] ?: sortedSetOf()
            msgs.add(message)
            messagesMap[date] = msgs
        }

    return messagesMap
}