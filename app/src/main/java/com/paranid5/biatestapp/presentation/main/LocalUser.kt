package com.paranid5.biatestapp.presentation.main

import androidx.compose.runtime.compositionLocalOf
import com.paranid5.biatestapp.data.retrofit.User

val LocalUser = compositionLocalOf { User() }