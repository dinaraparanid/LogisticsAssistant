package com.paranid5.biatestapp.data.utils.ext

import android.content.Context
import com.paranid5.biatestapp.R
import kotlinx.datetime.LocalDate

fun LocalDate.toDateFormatString(context: Context) =
    "$dayOfMonth ${monthNumber.getLocalizedMonth(context)}"

private fun Int.getLocalizedMonth(context: Context) = when (this) {
    1 -> context.resources.getString(R.string.january_msg)
    2 -> context.resources.getString(R.string.february_msg)
    3 -> context.resources.getString(R.string.march_msg)
    4 -> context.resources.getString(R.string.april_msg)
    5 -> context.resources.getString(R.string.may_msg)
    6 -> context.resources.getString(R.string.june_msg)
    7 -> context.resources.getString(R.string.july_msg)
    8 -> context.resources.getString(R.string.august_msg)
    9 -> context.resources.getString(R.string.september_msg)
    10 -> context.resources.getString(R.string.october_msg)
    11 -> context.resources.getString(R.string.november_msg)
    12 -> context.resources.getString(R.string.december_msg)
    else -> throw IllegalArgumentException("Month $this is not in range [1..12]")
}