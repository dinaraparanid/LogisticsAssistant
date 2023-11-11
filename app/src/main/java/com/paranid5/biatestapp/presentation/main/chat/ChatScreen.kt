package com.paranid5.biatestapp.presentation.main.chat

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewModelScope
import com.paranid5.biatestapp.presentation.ui.utils.ext.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val employer by chatViewModel.employerState.collectAsState()

    val isEmployerLoaded by remember {
        derivedStateOf { employer != null }
    }

    var layoutHeight by remember {
        mutableIntStateOf(0)
    }

    var editorHeight by remember {
        mutableIntStateOf(0)
    }

    val listMaxHeight by remember {
        derivedStateOf { layoutHeight - editorHeight }
    }

    val listState = rememberLazyListState()

    val itemsInListState = remember {
        mutableIntStateOf(0)
    }

    val newMessageIdState = remember {
        mutableIntStateOf(-1)
    }

    DisposableEffect(Unit) {
        val task = chatViewModel.viewModelScope.launch {
            chatViewModel.loadEmployer()

            while (true) {
                chatViewModel.loadMessagesFromNetwork()
                delay(1000)
            }
        }

        onDispose { task.cancel() }
    }

    ConstraintLayout(
        modifier.onGloballyPositioned {
            layoutHeight = it.size.height
        }
    ) {
        val (list, editor) = createRefs()

        if (isEmployerLoaded)
            MessagesList(
                chatViewModel = chatViewModel,
                itemsInListState = itemsInListState,
                listState = listState,
                newMessageIdState = newMessageIdState,
                modifier = Modifier
                    .heightIn(min = 0.dp, max = listMaxHeight.pxToDp())
                    .constrainAs(list) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(editor.top)
                    }
            )

        MessageEditor(
            chatViewModel = chatViewModel,
            itemsInListState = itemsInListState,
            listState = listState,
            newMessageIdState = newMessageIdState,
            modifier = Modifier
                .onGloballyPositioned { editorHeight = it.size.height }
                .constrainAs(editor) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}