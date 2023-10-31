package com.azmobile.androidcompose.customlayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable

@Composable
fun MyFlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            // Measure contents
            val placeables = measurables.map {
                it.measure(constraints)
            }

            // Group of placeables, 1 group is 1 row
            val groupPlaceables = mutableListOf<List<Placeable>>()
            var currentGroup = mutableListOf<Placeable>()
            var currentGroupWidth = 0

            // Create group of placeable,
            // the width of the whole group should be less than the parent width
            placeables.forEach { placeable ->
                if (currentGroupWidth + placeable.width <= constraints.maxWidth) {
                    currentGroup.add(placeable)
                    currentGroupWidth += placeable.width
                } else {
                    groupPlaceables.add(currentGroup)
                    currentGroup = mutableListOf(placeable)
                    currentGroupWidth = placeable.width
                }
            }
            // Add remaining row if there is
            if (currentGroup.isNotEmpty()) {
                groupPlaceables.add(currentGroup)
            }

            // Place contents
            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            ) {
                var yPos = 0
                groupPlaceables.forEach { row ->
                    var xPos = 0
                    row.forEach { placeable ->
                        placeable.place(
                            x = xPos,
                            y = yPos
                        )
                        xPos += placeable.width
                    }
                    yPos += row.maxOfOrNull { it.height } ?: 0
                }
            }
        },
        content = content
    )
}
