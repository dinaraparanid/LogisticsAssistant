package com.paranid5.biatestapp.presentation.ui.utils.ext

import com.paranid5.biatestapp.data.retrofit.tasks.OrderDateTime

fun OrderDateTime.toTimeStringFormat() =
    "$date • $time"