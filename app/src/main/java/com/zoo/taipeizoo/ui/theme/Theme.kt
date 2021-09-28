package com.zoo.taipeizoo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Purple400,
    primaryVariant = PurpleDark,
    onPrimary = Color.White,
    secondary = Blue300,
    secondaryVariant = BlueDark,
    onSecondary = Color.Black,
    error = Red800
)

private val DarkThemeColor = darkColors(
    background = Color.Black,
    primary = PurpleLight,
    primaryVariant = Purple400,
    onPrimary = Color.White,
    secondary = BlueLight,
    secondaryVariant = Blue300,
    onSecondary = Color.Black,
    error = Red500
)

@Composable
fun AppTheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    MaterialTheme(
        colors = if (isDark) DarkThemeColor else LightThemeColors,
        content = content
    )
}