package com.paranid5.biatestapp.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R

@Composable
fun AppLabel(modifier: Modifier = Modifier) =
    Row(modifier) {
        Image(
            painter = painterResource(id = R.drawable.bia_icon),
            contentDescription = stringResource(id = R.string.bia),
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically)
        )

        Spacer(Modifier.width(5.dp))

        Text(
            text = stringResource(id = R.string.logistics_assistant),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }