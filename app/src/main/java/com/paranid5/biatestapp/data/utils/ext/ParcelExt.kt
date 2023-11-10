package com.paranid5.biatestapp.data.utils.ext

import android.os.Parcel
import kotlinx.datetime.LocalDateTime

fun Parcel.readLocalDateTime() = LocalDateTime(
    year = readInt(),
    monthNumber = readInt(),
    dayOfMonth = readInt(),
    hour = readInt(),
    minute = readInt(),
)

fun Parcel.writeLocalDateTime(dateTime: LocalDateTime) {
    writeInt(dateTime.year)
    writeInt(dateTime.monthNumber)
    writeInt(dateTime.dayOfMonth)
    writeInt(dateTime.hour)
    writeInt(dateTime.minute)
}