package com.azmobile.androidcompose.drawing.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.azmobile.androidcompose.R
import com.azmobile.androidcompose.ui.theme.Purple40
import com.azmobile.androidcompose.ui.theme.PurpleGrey40

const val longTextSample =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque congue blandit arcu, nec pellentesque mauris accumsan vitae. Sed auctor feugiat nunc, ac iaculis turpis cursus non. Aenean scelerisque, magna et pharetra lobortis, sapien justo accumsan massa, sed dignissim magna est at mauris. Vestibulum id vulputate sapien, et molestie lectus. Vivamus vel ornare risus. Nam luctus tristique placerat. Vivamus luctus egestas justo, ut imperdiet ante laoreet ac. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos."

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawTextExample() {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        drawText(
            textMeasurer = textMeasurer,
            "Hello Compose",
            size = Size(300.dp.toPx(), 200.dp.toPx()),
            style = TextStyle(
                color = Color.White,
                fontSize = 30.sp
            )
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawMeasuredTextExample() {
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString(longTextSample),
                        constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                        style = TextStyle(fontSize = 18.sp)
                    )

                onDrawBehind {
                    drawRect(Purple40, size = measuredText.size.toSize())
                    drawText(measuredText)
                }
            }
            .fillMaxWidth()
            .height(410.dp)
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawOverflowTextExample() {
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString(longTextSample),
                        constraints = Constraints.fixed(
                            width = (size.width / 3f).toInt(),
                            height = size.height.toInt()
                        ),
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 18.sp)
                    )

                onDrawBehind {
                    drawRect(PurpleGrey40, size = measuredText.size.toSize())
                    drawText(measuredText)
                }
            }
            .fillMaxWidth()
            .height(500.dp)
    )
}

@Composable
fun DrawImageExample() {
    val pepeImage = ImageBitmap.imageResource(id = R.drawable.pepe)

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        scale(2f) {
            drawImage(
                pepeImage,
                topLeft = Offset(
                    center.x/2f,
                    center.y/2f
                )
            )
        }
    })
}

@Preview
@Composable
fun DrawTextPreview() {
    DrawTextExample()
}

@Preview
@Composable
fun DrawOverflowTextPreview() {
    DrawMeasuredTextExample()
}

@Preview
@Composable
fun DrawMeasuredTextPreview() {
    DrawOverflowTextExample()
}

@Preview
@Composable
fun DrawImagePreview() {
    DrawImageExample()
}
