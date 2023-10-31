package com.azmobile.androidcompose.drawing.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azmobile.androidcompose.ui.theme.AndroidComposeMaxMobileTheme

@Composable
fun SmileyFaceExample() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val canvasXCenter = center.x
        val canvasYCenter = center.y
//        drawContext.canvas
        rotate(
            degrees = 90f,
            pivot = Offset(
                canvasXCenter,
                canvasYCenter / 2f
            )
        ) {
            this.drawSmileyFace(canvasWidth, canvasHeight, canvasXCenter, canvasYCenter)
        }
//        this.drawSmileyFace(canvasWidth, canvasHeight, canvasXCenter, canvasYCenter)

        scale(
            scaleX = 1.5f,
            scaleY = 1f
        ) {
            this.drawSadFace(canvasWidth, canvasHeight, canvasXCenter, canvasYCenter)
        }
//        this.drawSadFace(canvasWidth, canvasHeight, canvasXCenter, canvasYCenter)
    }
}

private fun DrawScope.drawSmileyFace(
    canvasWidth: Float,
    canvasHeight: Float,
    canvasXCenter: Float,
    canvasYCenter: Float,
) {
    drawCircle( // Face
        color = Color.White,
        radius = canvasWidth / 2f,
        center = Offset(
            canvasXCenter,
            canvasYCenter / 2f
        )
    )
    inset(vertical = 10.dp.toPx()) {
        drawCircle( // Left eye
            color = Color.Yellow,
            radius = 20.dp.toPx(),
            center = Offset(
                canvasWidth / 4f,
                canvasYCenter / 3f
            )
        )
        drawCircle( // Right eye
            color = Color.Yellow,
            radius = 20.dp.toPx(),
            center = Offset(
                3 * canvasWidth / 4f,
                canvasYCenter / 3f
            )
        )
        drawArc( // Mouth
            color = Color.Yellow,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            size = size.copy(
                canvasWidth / 2f,
                canvasHeight / 4f
            ),
            topLeft = Offset(
                canvasWidth / 4f,
                canvasYCenter / 4f
            )
        )
    }
}

private fun DrawScope.drawSadFace(
    canvasWidth: Float,
    canvasHeight: Float,
    canvasXCenter: Float,
    canvasYCenter: Float,
) {
// Sad face
    drawCircle( // Face
        color = Color.Blue,
        radius = canvasWidth / 2f,
        center = Offset(
            canvasXCenter,
            3 * canvasYCenter / 2f
        )
    )
    drawCircle( // Left eye
        color = Color.Black,
        radius = 20.dp.toPx(),
        center = Offset(
            canvasWidth / 4f,
            4 * canvasYCenter / 3f
        )
    )
    drawCircle( // Right eye
        color = Color.Black,
        radius = 20.dp.toPx(),
        center = Offset(
            3 * canvasWidth / 4f,
            4 * canvasYCenter / 3f
        )
    )
    drawPath(
        path = getSadPath(size, center),
        color = Color.Black,
        style = Stroke(
            width = 8.dp.toPx(),
        )
    )
}

private fun getSadPath(parentSize: Size, parentCenter: Offset): Path {
    val path = Path()
    val curvePoint = 5 * parentCenter.y / 3
    path.moveTo(parentSize.width / 4f, 5 * parentCenter.y / 3)
    path.cubicTo(
        x1 = parentCenter.x - 50f, y1 = curvePoint - 100f,
        x2 = parentCenter.x + 50f, y2 = curvePoint - 100f,
        x3 = 3 * parentSize.width / 4f, y3 = 5 * parentCenter.y / 3
    )
    return path
}

@Preview
@Composable
fun CoordinateSystemExamplePreview() {
    AndroidComposeMaxMobileTheme {
        Column {
            SmileyFaceExample()
        }
    }
}
