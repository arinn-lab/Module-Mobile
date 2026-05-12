package com.example.songpediacompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Brown,
    onPrimary = White,
    secondary = BrownLight,
    onSecondary = White,
    background = White,
    onBackground = TextDark,
    surface = CardColor,
    onSurface = TextDark,
)

@Composable
fun SongPediaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}