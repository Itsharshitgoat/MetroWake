package com.metrowake.app.presentation.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class PerforatedTicketShape(
    private val cornerRadius: Float = 24f,
    private val holeRadius: Float = 16f,
    private val verticalOffsetRatio: Float = 0.5f // Centers the holes by default
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val yPos = size.height * verticalOffsetRatio

            // Top Left
            moveTo(0f, cornerRadius)
            quadraticTo(0f, 0f, cornerRadius, 0f)

            // Top Right
            lineTo(size.width - cornerRadius, 0f)
            quadraticTo(size.width, 0f, size.width, cornerRadius)

            // Right edge down to hole
            lineTo(size.width, yPos - holeRadius)
            // Right hole (semi-circle cutting inwards)
            arcTo(
                rect = Rect(
                    left = size.width - holeRadius,
                    top = yPos - holeRadius,
                    right = size.width + holeRadius,
                    bottom = yPos + holeRadius
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            // Right edge down to bottom
            lineTo(size.width, size.height - cornerRadius)
            quadraticTo(size.width, size.height, size.width - cornerRadius, size.height)

            // Bottom edge to Left edge
            lineTo(cornerRadius, size.height)
            quadraticTo(0f, size.height, 0f, size.height - cornerRadius)

            // Left edge up to hole
            lineTo(0f, yPos + holeRadius)
            // Left hole (semi-circle cutting inwards)
            arcTo(
                rect = Rect(
                    left = -holeRadius,
                    top = yPos - holeRadius,
                    right = holeRadius,
                    bottom = yPos + holeRadius
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            lineTo(0f, cornerRadius)
            close()
        }
        return Outline.Generic(path)
    }
}
