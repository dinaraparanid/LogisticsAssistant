package com.paranid5.biatestapp.presentation.ui.utils.ext

import com.paranid5.biatestapp.data.retrofit.tasks.Destination
import java.util.Locale

inline val Destination.shortStartLocation
    get() = startLocation
        .split(", ")
        .drop(2)
        .let {
            val dest = it.toMutableList()

            dest[0] = dest[0].replaceFirstChar { c ->
                when {
                    c.isLowerCase() -> c.titlecase(Locale.ROOT)
                    else -> c.toString()
                }
            }

            dest
        }
        .joinToString(", ")