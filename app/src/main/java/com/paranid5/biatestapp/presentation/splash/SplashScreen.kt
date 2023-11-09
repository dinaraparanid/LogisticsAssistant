package com.paranid5.biatestapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors

@Preview
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current.value

    Surface(
        modifier = modifier,
        color = colors.background
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.bia_icon),
                    contentDescription = stringResource(id = R.string.bia),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(70.dp)
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = stringResource(id = R.string.logistics_assistant),
                    color = colors.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}