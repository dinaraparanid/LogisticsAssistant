package com.paranid5.biatestapp.data.retrofit.tasks

import android.os.Parcelable
import com.paranid5.biatestapp.data.utils.ext.parseSimpleDate
import com.paranid5.biatestapp.data.utils.ext.parseSimpleTime
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDateTime(val date: String, val time: String) : Parcelable, Comparable<OrderDateTime> {
    @IgnoredOnParcel
    val localDate = parseSimpleDate(date)

    @IgnoredOnParcel
    val localTime = parseSimpleTime(time)

    override fun compareTo(other: OrderDateTime) = when {
        localDate != other.localDate -> localTime.compareTo(other.localTime)
        else -> localDate.compareTo(other.localDate)
    }
}
