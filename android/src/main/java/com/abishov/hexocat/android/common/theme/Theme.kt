package com.abishov.hexocat.android.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color as ComposeColor

object HexocatPalette {
    val Light = lightColors(
        primary = Color.Grey400,
        primaryVariant = Color.Grey600,
        onPrimary = ComposeColor.White,
        secondary = Color.Blue400,
        secondaryVariant = Color.Blue600,
        onSecondary = ComposeColor.White,
    )
}

@Composable
fun HexocatTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colors = HexocatPalette.Light,
        typography = HexocatTypography
    )
}
