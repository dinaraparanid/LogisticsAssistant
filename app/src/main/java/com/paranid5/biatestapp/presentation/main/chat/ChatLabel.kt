package com.paranid5.biatestapp.presentation.main.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.LocalEmployer
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily

@Composable
fun ChatLabel(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value

    Row(modifier.background(colors.background)) {
        Box(
            Modifier
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                .align(Alignment.CenterVertically)
        ) {
            EmployerAvatar(Modifier.size(36.dp))
        }

        EmployerName(
            Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun EmployerAvatar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val employer = LocalEmployer.current

    AsyncImage(
        contentDescription = stringResource(id = R.string.employer),
        contentScale = ContentScale.FillBounds,
        alignment = Alignment.Center,
        modifier = modifier.clip(CircleShape),
        model = ImageRequest.Builder(context)
            .data(employer.avatarUrl)
            .placeholder(R.drawable.profile_icon)
            .fallback(R.drawable.profile_icon)
            .error(R.drawable.profile_icon)
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .precision(Precision.EXACT)
            .scale(Scale.FILL)
            .crossfade(400)
            .build(),
    )
}

@Composable
private fun EmployerName(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val employer = LocalEmployer.current

    Text(
        text = employer.name,
        color = colors.primary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = StolzlFontFamily,
        modifier = modifier
    )
}