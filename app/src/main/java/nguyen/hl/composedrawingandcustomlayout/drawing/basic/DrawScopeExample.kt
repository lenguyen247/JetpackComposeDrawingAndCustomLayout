package com.azmobile.androidcompose.drawing.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azmobile.androidcompose.ui.theme.AndroidComposeMaxMobileTheme

@Composable
fun DrawScopeExample() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasQuadrantSize = size / 2F
        drawRect(
            color = Color.Magenta,
            size = canvasQuadrantSize
        )
    }
}

@Composable
fun DrawScopeExample2() {
    // Sample showing how to use the DrawScope receiver scope to issue
    // drawing commands
    Canvas(Modifier.size(300.dp)) {
        size
        val x = center.x
        val y = center.y

        drawRect(color = Color.Gray) // Draw grey background
        // Inset content by 10 pixels on the left/right sides and 12 by the
        // top/bottom
        inset(20.0f, 12.0f) {
            val quadrantSize = size / 2.0f

            // Draw a rectangle within the inset bounds
            drawRect(
                size = quadrantSize,
                color = Color.Red
            )

            rotate(45.0f) {
                drawRect(size = quadrantSize, color = Color.Blue)
            }
        }
    }
}

@Preview
@Composable
fun DrawScopeExamplePreview() {
    AndroidComposeMaxMobileTheme {
        DrawScopeExample()
    }
}

@Preview
@Composable
fun DrawScopeExample2Preview() {
    AndroidComposeMaxMobileTheme {
        DrawScopeExample2()
    }
}
