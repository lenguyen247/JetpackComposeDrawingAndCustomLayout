package com.azmobile.androidcompose.customlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.azmobile.androidcompose.ui.theme.AndroidComposeMaxMobileTheme

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    AndroidComposeMaxMobileTheme {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Gray,
                    shape = RectangleShape
                )
        ) {
            Text(
                "Hi there!",
                Modifier.firstBaselineToTop(32.dp),
                color = Color.Yellow
            )
        }
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    AndroidComposeMaxMobileTheme {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Gray,
                    shape = RectangleShape
                )
        )  {
            Text(
                "Hi there!",
                Modifier.padding(top = 32.dp),
                color = Color.Yellow
            )
        }
    }
}
