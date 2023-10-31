package com.azmobile.androidcompose.drawing.sample

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azmobile.androidcompose.ui.theme.AndroidComposeMaxMobileTheme
import com.azmobile.androidcompose.ui.theme.BarColor
import com.azmobile.androidcompose.ui.theme.PurpleGrey40

val graphData = listOf(18, 12, 44, 56, 78, 122, 100, 111, 60, 150)

@Composable
fun MyLineGraph() {
    Box(
        modifier = Modifier
            .background(PurpleGrey40)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Using Canvas
        Canvas(
            modifier = Modifier
                .padding(8.dp)
                .aspectRatio(3 / 2f) // keep in 3/2 ratio
                .fillMaxSize(),
        ) {
            GraphBackground()
        }
        // Using Box + drawWithCache()
        LineGraph(graphData)
    }
}

fun DrawScope.GraphBackground() {
    // Draw the border
    val barWidthDp = 1.dp.toPx()
    drawRect(BarColor, style = Stroke(barWidthDp))

    // Draw vertical lines
    val verticalLines = 4
    // size is the size of the Canvas (width, height)
    val verticalSize = size.width / (verticalLines + 1)
    repeat(verticalLines) { i ->
        val startX = verticalSize * (i + 1)
        drawLine(
            BarColor,
            start = Offset(startX, 0f),
            end = Offset(startX, size.height),
            strokeWidth = barWidthDp
        )
    }
    // Draw horizontal lines
    val horizontalLines = 3
    val sectionSize = size.height / (horizontalLines + 1)
    repeat(horizontalLines) { i ->
        val startY = sectionSize * (i + 1)
        drawLine(
            BarColor,
            start = Offset(0f, startY),
            end = Offset(size.width, startY),
            strokeWidth = barWidthDp
        )
    }
}

@Composable
fun LineGraph(data: List<Int>) {
    val animationProgress = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = graphData, block = {
        animationProgress.animateTo(1f, tween(3000))
    })

    Spacer(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(3 / 2f)
            .fillMaxSize()
            // use this to prevent any object re-initialize when
            // re-compose happen
            .drawWithContent {

            }
            .drawBehind {

            }
            .drawWithCache {
                val path = generatePath(data, size)
                val smoothPath = generateSmoothPath(data, size)

                val filledPath = Path()
                filledPath.addPath(path)
                filledPath.lineTo(size.width, size.height)
                filledPath.lineTo(0f, size.height)
                filledPath.close()

                val brush = Brush.verticalGradient(
                    listOf(
                        Color.Green.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )

                onDrawBehind {
                    clipRect(right = size.width * animationProgress.value) {
                        drawPath(
                            path,
                            Color.Green,
                            style = Stroke(2.dp.toPx())
                        )
//                    drawPath(
//                        smoothPath,
//                        Color.Green,
//                        style = Stroke(2.dp.toPx())
//                    )
                        drawPath(
                            filledPath,
                            brush = brush,
                            style = Fill
                        )
                    }
                }
            }
    )
}

@Preview
@Composable
fun PreviewMyLineGraph() {
    AndroidComposeMaxMobileTheme {
        MyLineGraph()
    }
}

fun generatePath(graphData: List<Int>, size: Size): Path {
    val path = Path()
    val width = size.width
    val height = size.height

    val maxYValue = graphData.maxBy { it }.toFloat()
    val maxXValue = (graphData.size).toFloat()

    path.moveTo(0f, height)
    graphData.forEachIndexed { i, data ->
        val iTrue = i + 1
        val x = (iTrue / maxXValue) * width
        val y = height - (data / maxYValue) * height

        path.lineTo(x, y)
    }
    return path
}

fun generateSmoothPath(graphData: List<Int>, size: Size): Path {
    val path = Path()
    val width = size.width
    val height = size.height

    val maxYValue = graphData.maxBy { it }.toFloat()
    val maxXValue = (graphData.size).toFloat()

    path.moveTo(0f, height)
    graphData.forEachIndexed { i, data ->
        val iTrue = i + 1
        val x = (iTrue / maxXValue) * width
        val y = height - (data / maxYValue) * height

        // Calculate control points closer to the data points
        val prevX = if (iTrue > 0) (iTrue - 1) / maxXValue * width else 0f
        val nextX = if (iTrue < graphData.size - 1) (iTrue + 1) / maxXValue * width else width
        val controlPoint1X = (prevX + x) / 2 + (x - prevX) * 0.1f
        val controlPoint1Y = y
        val controlPoint2X = (x + nextX) / 2 - (nextX - x) * 0.6f
        val controlPoint2Y = y

        path.cubicTo(
            controlPoint1X, controlPoint1Y,
            controlPoint2X, controlPoint2Y,
            x, y
        )
    }
    return path
}
