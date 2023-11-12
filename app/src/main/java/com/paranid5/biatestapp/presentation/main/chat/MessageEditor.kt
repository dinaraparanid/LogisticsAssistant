package com.paranid5.biatestapp.presentation.main.chat

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessageNotDisposed
import com.paranid5.biatestapp.presentation.main.composition_locals.LocalNewMessagesAmountShown
import com.paranid5.biatestapp.presentation.ui.theme.LightGray
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.MiddleGray
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun MessageEditor(
    chatViewModel: ChatViewModel,
    itemsInListState: State<Int>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val newMessageNotDisposedState = LocalNewMessageNotDisposed.current
    val newMessagesAmountShownState = LocalNewMessagesAmountShown.current

    val coroutineScope = rememberCoroutineScope()
    val text by chatViewModel.messageState.collectAsState()

    val itemsInList by itemsInListState

    val isSendButtonEnabled by remember {
        derivedStateOf { text.isNotEmpty() }
    }

    val sendButtonAlpha by animateFloatAsState(
        targetValue = when {
            isSendButtonEnabled -> 1F
            else -> 0F
        },
        animationSpec = tween(300, easing = LinearEasing),
        label = ""
    )

    Row(modifier.background(colors.background)) {
        TextField(
            value = text,
            onValueChange = { chatViewModel.setMessage(it) },
            textStyle = TextStyle(
                color = colors.primary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_message),
                    color = MiddleGray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = StolzlFontFamily,
                )
            }
        )

        Spacer(Modifier.width(8.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = LightGray),
            modifier = Modifier
                .alpha(sendButtonAlpha)
                .padding(top = 17.dp, bottom = 17.dp, end = 16.dp)
        ) {
            Box(Modifier.padding(8.dp)) {
                IconButton(
                    enabled = isSendButtonEnabled,
                    onClick = {
                        newMessageNotDisposedState.update { false }
                        newMessagesAmountShownState.update { 0 }

                        coroutineScope.launch {
                            chatViewModel.sendMessage()
                            listState.scrollToItem(itemsInList)
                        }
                    },
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.send_message),
                        contentDescription = stringResource(id = R.string.send_message),
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}