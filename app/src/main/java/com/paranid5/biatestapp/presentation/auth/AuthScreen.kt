package com.paranid5.biatestapp.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.R

private const val ENTER_REGION_MAX_LEN = 10
private const val PAT_PHONE_NUMBER = "+7 (999) 000 - 00 - 00"

@Preview
@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    val phoneNumberState = rememberSaveable { mutableStateOf("") }

    Box(modifier) {
        AppLabel(Modifier.padding(top = 45.dp, start = 16.dp))

        NumberEnterLabel(
            phoneNumberState = phoneNumberState,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        ConfirmPhoneButton(
            phoneNumberState = phoneNumberState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AppLabel(modifier: Modifier = Modifier) =
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

@Composable
private fun NumberEnterLabel(
    phoneNumberState: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.welcome_label),
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.enter_number_for_auth),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )

        Spacer(Modifier.height(24.dp))

        NumberEditor(
            phoneNumberState = phoneNumberState,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun NumberEditor(phoneNumberState: MutableState<String>, modifier: Modifier = Modifier) =
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        BasicTextField(
            value = phoneNumberState.value,
            textStyle = TextStyle(fontSize = 16.sp),
            onValueChange = { phoneNumberState.value = it.take(ENTER_REGION_MAX_LEN) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = ::mobileNumberFilter,
            modifier = Modifier.padding(16.dp)
        )
    }

private fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    val trimmed = text.text.take(PAT_PHONE_NUMBER.length)

    val annotatedString = AnnotatedString.Builder().run {
        append("+7 (")

        trimmed.forEachIndexed { index, c ->
            when (index) {
                2 -> append("$c) ")
                5, 7 -> append("$c - ")
                else -> append(c)
            }
        }

        pushStyle(SpanStyle(color = Color.LightGray))

        val left = PAT_PHONE_NUMBER.length - length
        append(PAT_PHONE_NUMBER.takeLast(left))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int) = when {
            offset <= 2 -> offset + 4
            offset <= 6 -> offset + 6
            offset <= 8 -> offset + 9
            offset <= 10 -> offset + 12
            else -> 22
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (text.isEmpty()) return 0

            return when {
                offset <= 3 -> 0
                offset <= 6 -> (offset - 4)
                offset <= 8 -> (offset - 5)
                offset <= 12 -> (offset - 6)
                offset <= 15 -> (offset - 7)
                offset <= 18 -> (offset - 8)
                else -> 10
            }.coerceIn(0..text.length)
        }
    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}

@Composable
private fun ConfirmPhoneButton(
    phoneNumberState: State<String>,
    modifier: Modifier = Modifier
) {
    val phoneNumber by phoneNumberState

    val isButtonEnabled by remember {
        derivedStateOf { phoneNumber.length == ENTER_REGION_MAX_LEN }
    }

    Button(
        modifier = modifier,
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            disabledContainerColor = Color.LightGray
        ),
        onClick = {
            // TODO: Confirm phone number
        }
    ) {
        Text(
            text = stringResource(id = R.string.contine),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(16.dp)
        )
    }
}