package com.example.primarydetail.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(
    primary = ColorPrimary,
    secondary = ColorAccent,
)

private val DarkThemeColors = darkColors(
    primary = ColorPrimaryDark,
    secondary = ColorAccent,
)

@Composable
fun PrimaryDetailTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}