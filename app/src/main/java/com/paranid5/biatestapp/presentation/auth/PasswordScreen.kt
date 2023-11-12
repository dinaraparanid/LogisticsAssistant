package com.paranid5.biatestapp.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R
import com.paranid5.biatestapp.presentation.main.MainActivity
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.pxToDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ENTER_REGION_MAX_LEN = 6
private const val HINT_PASSWORD = "＿＿＿＿＿＿"

@Composable
fun PasswordScreen(authViewModel: AuthViewModel, modifier: Modifier = Modifier) {
    val isPasswordCorrectState = remember {
        mutableStateOf<Boolean?>(null)
    }

    Box(modifier) {
        AppLabel(Modifier.padding(top = 45.dp, start = 16.dp))

        PasswordEnterLabel(
            authViewModel = authViewModel,
            isPasswordCorrectState = isPasswordCorrectState,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        ConfirmPasswordButton(
            authViewModel = authViewModel,
            isPasswordCorrectState = isPasswordCorrectState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun PasswordEnterLabel(
    authViewModel: AuthViewModel,
    isPasswordCorrectState: State<Boolean?>,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val navController = LocalAuthNavController.current

    Column(modifier) {
        Row(Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { navController.navigateToLoginScreen() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left_line),
                    contentDescription = stringResource(id = R.string.back),
                    tint = colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = stringResource(id = R.string.enter_password),
                color = colors.primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.obtain_password),
            color = colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily
        )

        Spacer(Modifier.height(24.dp))

        PasswordEditor(
            authViewModel = authViewModel,
            isPasswordCorrectState = isPasswordCorrectState,
            modifier = Modifier.fillMaxWidth()
        )

        if (isPasswordCorrectState.value == false)
            Text(
                text = stringResource(id = R.string.incorrect_password),
                color = colors.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                modifier = Modifier.padding(top = 8.dp)
            )
    }
}

@Composable
fun PasswordEditor(
    authViewModel: AuthViewModel,
    isPasswordCorrectState: State<Boolean?>,
    modifier: Modifier = Modifier
)  {
    val colors = LocalAppColors.current.value

    val password by authViewModel.passwordState.collectAsState()
    val isPasswordCorrect by isPasswordCorrectState

    var passwordEditorHeight by remember {
        mutableIntStateOf(-1)
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            2.dp,
            when (isPasswordCorrect) {
                false -> colors.secondary
                else -> colors.primary
            }
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        BasicTextField(
            value = password,
            textStyle = TextStyle(
                fontSize = 16.sp,
                letterSpacing = 10.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = StolzlFontFamily,
                color = colors.primary
            ),
            cursorBrush = SolidColor(colors.primary),
            onValueChange = { authViewModel.setPassword(it.take(ENTER_REGION_MAX_LEN)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = { passwordFilter(it, colors) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .then(
                    when (passwordEditorHeight) {
                        -1 -> Modifier
                        else -> Modifier.height(passwordEditorHeight.pxToDp())
                    }
                )
                .onGloballyPositioned {
                    if (passwordEditorHeight == -1)
                        passwordEditorHeight = it.size.height
                }
        )
    }
}

private fun passwordFilter(text: AnnotatedString, colors: ColorScheme): TransformedText {
    val trimmed = text.text.take(HINT_PASSWORD.length)

    val annotatedString = AnnotatedString.Builder().run {
        append(trimmed)
        pushStyle(SpanStyle(color = colors.primary, fontWeight = FontWeight.Bold))

        val left = HINT_PASSWORD.length - length
        append(HINT_PASSWORD.takeLast(left))

        toAnnotatedString()
    }

    val passwordOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int) = offset

        override fun transformedToOriginal(offset: Int) =
            offset.coerceIn(0..text.length)
    }

    return TransformedText(annotatedString, passwordOffsetTranslator)
}

@Composable
private fun ConfirmPasswordButton(
    authViewModel: AuthViewModel,
    isPasswordCorrectState: MutableState<Boolean?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = LocalAppColors.current.value

    val password by authViewModel.passwordState.collectAsState()
    val isPasswordCorrect by isPasswordCorrectState

    val isButtonEnabled by remember {
        derivedStateOf { password.length == ENTER_REGION_MAX_LEN && isPasswordCorrect != false }
    }

    val coroutineScope = rememberCoroutineScope()

    val vibrator = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val manager = context.getSystemService(
                Context.VIBRATOR_MANAGER_SERVICE
            ) as VibratorManager

            manager.defaultVibrator
        }

        else -> context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    LaunchedEffect(key1 = password) {
        isPasswordCorrectState.value = null
    }

    Row(modifier) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                disabledContainerColor = Color.LightGray
            ),
            onClick = {
                coroutineScope.launch {
                    isPasswordCorrectState.value = withContext(Dispatchers.IO) {
                        authViewModel.isPasswordCorrect()
                    }

                    when {
                        isPasswordCorrect == true -> context.startActivity(
                            Intent(context, MainActivity::class.java).putExtra(
                                MainActivity.USER_KEY,
                                authViewModel.userState.value
                            )
                        )

                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                200,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )

                        else -> vibrator.vibrate(200)
                    }
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.contine),
                color = colors.background,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = StolzlFontFamily,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
            )
        }
    }
}