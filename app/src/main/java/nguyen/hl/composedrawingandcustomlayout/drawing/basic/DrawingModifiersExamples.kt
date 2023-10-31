package com.azmobile.androidcompose.drawing.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azmobile.androidcompose.ui.theme.AndroidComposeMaxMobileTheme


@Composable
fun DrawWithContentExample() {
    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput("dragging") {
                detectDragGestures { change, dragAmount ->
                    pointerOffset += dragAmount
                }
            }
            .onSizeChanged {
                pointerOffset = Offset(it.width / 2f, it.height / 2f)
            }
            .drawWithContent {
                // must call
                drawContent()
                // draws a fully black area with a small keyhole at pointerOffset
                // that’ll show part of the UI.
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = pointerOffset,
                        radius = 100.dp.toPx(),
                    )
                )
//                drawContent()
            }
    ) {
        repeat(10) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = "This is an Text",
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DrawBehindExample() {
    Text(
        "Hello Compose!",
        fontSize = 40.sp,
        modifier = Modifier
            .drawBehind {
                this.drawRoundRect(
                    Color(0xFFBBAAEE),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(12.dp)
    )
//    Canvas(modifier = , onDraw = )
}

@Composable
fun DrawWithCacheExample() {
    Text(
        "Hello Compose!",
        fontSize = 40.sp,
        modifier = Modifier
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E82F0),
                        Color(0xFF42A5F5)
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
            }
            .padding(12.dp)
    )
}

@Preview
@Composable
fun DrawWithContentPreview() {
    AndroidComposeMaxMobileTheme {
        DrawWithContentExample()
    }
}

@Preview
@Composable
fun DrawBehindPreview() {
    AndroidComposeMaxMobileTheme {
        DrawBehindExample()
    }
}

@Preview
@Composable
fun DrawWithCachePreview() {
    AndroidComposeMaxMobileTheme {
        DrawWithCacheExample()
    }
}
