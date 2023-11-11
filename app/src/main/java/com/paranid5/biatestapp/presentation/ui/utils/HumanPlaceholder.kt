package com.paranid5.biatestapp.presentation.ui.utils

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.paranid5.biatestapp.R

@Composable
fun HumanPlaceholder(
    tint: Color,
    contentDescription: String? = stringResource(id = R.string.profile)
) = Icon(
    painter = painterResource(id = R.drawable.profile_icon),
    contentDescription = contentDescription,
    tint = tint
)