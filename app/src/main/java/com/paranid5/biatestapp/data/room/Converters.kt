package com.paranid5.biatestapp.data.room

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class Converters {
    @TypeConverter
    fun toLocalDateTime(epochSecs: Long) = Instant
        .fromEpochSeconds(epochSecs)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime) = dateTime
        .toInstant(TimeZone.currentSystemDefault())
        .epochSeconds
}