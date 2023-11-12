package com.paranid5.biatestapp.presentation.main.tasks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paranid5.biatestapp.data.retrofit.tasks.Destination
import com.paranid5.biatestapp.presentation.ui.theme.GrayDestinationTime
import com.paranid5.biatestapp.presentation.ui.theme.LocalAppColors
import com.paranid5.biatestapp.presentation.ui.theme.StolzlFontFamily
import com.paranid5.biatestapp.presentation.ui.utils.ext.shortStartLocation
import com.paranid5.biatestapp.presentation.ui.utils.ext.toTimeStringFormat

@Composable
fun TaskShortDestinations(
    destinations: List<Destination>,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val startYPosState = remember { mutableFloatStateOf(0F) }
    val endYPosState = remember { mutableFloatStateOf(0F) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.onBackground)
    ) {
        Row(Modifier.fillMaxWidth()) {
            DestinationsAmountLine(
                destinationsAmount = destinations.size,
                startYPosState = startYPosState,
                endYPosState = endYPosState,
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight()
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
                    .background(Color.Green)
            )

            Spacer(Modifier.width(8.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                DestinationDescription(
                    destination = destinations.first(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            startYPosState.floatValue = it.positionInParent().y + it.size.height / 4
                        }
                )

                DestinationDescription(
                    destination = destinations.last(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            endYPosState.floatValue = it.positionInParent().y + it.size.height / 4
                        }
                )
            }
        }
    }
}

@Composable
private fun DestinationsAmountLine(
    destinationsAmount: Int,
    startYPosState: FloatState,
    endYPosState: FloatState,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value
    val textMeasurer = rememberTextMeasurer()

    val startYPos by startYPosState
    val endYPos by endYPosState
    val destinationsShown = destinationsAmount - 2

    val textLayout = textMeasurer.measure(
        text = destinationsShown.toString(),
        style = TextStyle(
            fontSize = 8.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    )

    Canvas(modifier) {
        drawCircle(
            color = colors.onPrimary,
            radius = 8F,
            center = Offset(center.x, startYPos),
            style = Stroke(width = 1F)
        )

        drawLine(
            color = colors.onPrimary,
            start = Offset(center.x, startYPos + 7.5F),
            end = Offset(center.x, endYPos),
            strokeWidth = 0.5F
        )

        if (destinationsShown > 0) {
            val centerY = (startYPos + endYPos) / 2

            drawCircle(
                color = colors.onPrimary,
                radius = 16F,
                center = Offset(center.x, centerY),
                style = Stroke(width = 1F)
            )

            drawCircle(
                color = colors.onBackground,
                radius = 15F,
                center = Offset(center.x, centerY),
                style = Fill
            )

            drawText(
                textLayoutResult = textLayout,
                color = colors.onPrimary,
                topLeft = Offset(
                    x = center.x - textLayout.size.width / 1.75F,
                    y = centerY - textLayout.size.height / 1.75F
                ),
            )
        }

        drawCircle(
            color = colors.onPrimary,
            radius = 8F,
            center = Offset(center.x, endYPos),
            style = Fill
        )
    }
}

@Composable
private fun DestinationDescription(
    destination: Destination,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current.value

    Column(modifier) {
        Text(
            text = destination.shortStartLocation,
            color = colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = destination.loadingTime.toTimeStringFormat(),
            color = GrayDestinationTime,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = StolzlFontFamily,
        )
    }
}