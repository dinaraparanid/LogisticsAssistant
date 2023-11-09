package com.paranid5.biatestapp.presentation.ui.utils.ext

fun String.toPhoneNumberFormat() =
    "+7 ${take(3)} ${substring(3..5)}-${substring(6..7)}-${takeLast(2)}"