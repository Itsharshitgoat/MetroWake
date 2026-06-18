package com.metrowake.app.presentation.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TicketShape(
    private val cornerRadius: Float = 24f,
    private val toothWidth: Float = 30f,
    private val toothHeight: Float = 15f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Top Left
            moveTo(0f, cornerRadius)
            quadraticTo(0f, 0f, cornerRadius, 0f)

            // Top Right
            lineTo(size.width - cornerRadius, 0f)
            quadraticTo(size.width, 0f, size.width, cornerRadius)

            // Bottom Right
            lineTo(size.width, size.height)

            // Zig-zag Bottom Edge
            var currentX = size.width
            var goingUp = true
            while (currentX > 0) {
                currentX -= toothWidth / 2f
                val y = if (goingUp) size.height - toothHeight else size.height
                lineTo(currentX, y)
                goingUp = !goingUp
            }

            // Adjust back to exactly 0 on X axis if overshot
            lineTo(0f, size.height)

            // Bottom Left
            lineTo(0f, cornerRadius)
            close()
        }
        return Outline.Generic(path)
    }
}
