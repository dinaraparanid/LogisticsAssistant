package com.paranid5.biatestapp.data.utils.ext

import kotlinx.datetime.LocalTime

fun LocalTime.toTimeFormatString() =
    "${hour.extendedWithZeroes}:${minute.extendedWithZeroes}"

private inline val Int.extendedWithZeroes
    get() = if (this < 10) "0$this" else toString()