package com.paranid5.biatestapp.data.utils.ext

import kotlinx.datetime.LocalTime

fun LocalTime.toTimeFormatString() =
    "${hour.extendedWithZeroes}:${minute.extendedWithZeroes}"

fun parseSimpleTime(timeStr: String): LocalTime {
    val (hour, minute) = timeStr.split(":").map(String::toInt)
    return LocalTime(hour = hour, minute = minute)
}

private inline val Int.extendedWithZeroes
    get() = if (this < 10) "0$this" else toString()