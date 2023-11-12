package com.paranid5.biatestapp.presentation.ui.utils.ext

import android.content.Context
import com.paranid5.biatestapp.R

fun Boolean.toLocalizedYNString(context: Context) = when {
    this -> context.resources.getString(R.string.yes)
    else -> context.resources.getString(R.string.no)
}