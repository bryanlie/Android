package com.example.codapizza.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme(
        primary = Color(0xFFB72A33),
        secondary = Color(0xFF03C4DD),
    )

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
