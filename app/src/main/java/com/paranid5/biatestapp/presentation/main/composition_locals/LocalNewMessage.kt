package com.paranid5.biatestapp.presentation.main.composition_locals

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

val LocalNewMessageNotDisposed = compositionLocalOf { MutableStateFlow(true) }

val LocalNewMessagesAmountShown = compositionLocalOf { MutableStateFlow(0) }