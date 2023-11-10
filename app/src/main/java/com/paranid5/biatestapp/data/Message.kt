package com.paranid5.biatestapp.data

import android.os.Parcelable
import kotlinx.datetime.LocalDateTime

interface Message : Parcelable, Comparable<Message> {
    val fromUserId: Long
    val toUserId: Long
    val text: String
    val sendTime: LocalDateTime
    val read: Boolean

    override fun compareTo(other: Message) = sendTime.compareTo(other.sendTime)
}