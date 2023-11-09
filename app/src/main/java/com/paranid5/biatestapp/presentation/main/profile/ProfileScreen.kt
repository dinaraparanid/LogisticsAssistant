package com.paranid5.biatestapp.presentation.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.LocalMainActivity
import com.paranid5.biatestapp.presentation.main.LocalUser
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.toPhoneNumberFormat

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        MainUserInfo(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        )

        UserDescription(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )

        Notifications(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )

        SignOut(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

// ------------------------- Main User Info -------------------------

@Composable
private fun MainUserInfo(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                UserPortrait(Modifier.size(68.dp))
            }

            UserNameJob(
                Modifier
                    .padding(
                        top = 12.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun UserPortrait(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val user = LocalUser.current

    AsyncImage(
        contentDescription = stringResource(id = R.string.profile),
        contentScale = ContentScale.FillBounds,
        alignment = Alignment.Center,
        modifier = modifier.clip(CircleShape),
        model = ImageRequest.Builder(context)
            .data(user.portraitUrl)
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
private fun UserNameJob(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val user = LocalUser.current

    Column(modifier) {
        Text(
            text = user.name,
            color = colors.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = user.job,
            color = colors.onPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}

// ------------------------- User Description -------------------------

@Composable
private fun UserDescription(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value
    val user = LocalUser.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background)
    ) {
        Column(Modifier.fillMaxWidth()) {
            UserDescriptionItem(
                title = stringResource(id = R.string.employee_id),
                userData = user.employeeId.toString(),
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )

            UserDescriptionItem(
                title = stringResource(id = R.string.phone_number),
                userData = user.phoneNumber.toPhoneNumberFormat(),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            UserDescriptionItem(
                title = stringResource(id = R.string.citizenship),
                userData = user.citizenship,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            UserDescriptionItem(
                title = stringResource(id = R.string.car),
                userData = user.car,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            UserDescriptionItem(
                title = stringResource(id = R.string.car_number),
                userData = user.carNumber,
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        }
    }
}

@Composable
private fun UserDescriptionItem(
    title: String,
    userData: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = userData,
            color = colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    }
}

// ------------------------- Notifications + Sign Out -------------------------

@Composable
private fun Notifications(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background),
        modifier = modifier.clickable {
            // TODO: че-то должно произойти
        }
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.notifications),
                color = colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterStart)
            )

            Box(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = 10.dp, bottom = 10.dp, end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_s_line),
                    contentDescription = stringResource(id = R.string.sign_out),
                    tint = colors.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun SignOut(modifier: Modifier = Modifier) {
    val activity = LocalMainActivity.current
    val colors = LocalAppColors.current.value

    Card(
        modifier = modifier.clickable { activity?.finish() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.background)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = colors.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterStart)
            )

            Box(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = 10.dp, bottom = 10.dp, end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_s_line),
                    contentDescription = stringResource(id = R.string.sign_out),
                    tint = colors.secondary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}