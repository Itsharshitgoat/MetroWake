package com.metrowake.app.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryNavy = Color(0xFF1A365D)
val SecondaryOrange = Color(0xFFC05621)
val SuccessGreen = Color(0xFF2F855A)
val DangerRed = Color(0xFFC53030)
val NeutralDark = Color(0xFF2D3748)
val BackgroundColor = Color(0xFFF4F6FB)
val SurfaceColor = Color(0xFFFFFFFF)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryNavy,
    secondary = SecondaryOrange,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = NeutralDark,
    onSurface = NeutralDark,
    error = DangerRed
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryNavy,
    secondary = SecondaryOrange,
    background = NeutralDark,
    surface = Color(0xFF1A202C),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BackgroundColor,
    onSurface = BackgroundColor,
    error = DangerRed
)

@Composable
fun MetroWakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
