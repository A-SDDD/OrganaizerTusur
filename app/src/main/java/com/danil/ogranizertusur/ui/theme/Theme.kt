package com.danil.ogranizertusur.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.danil.ogranizertusur.schedule.ThemeStatusObj

private val DarkColorPalette = darkColors(
    primary = DarkBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = darkBack,
    surface = NoteColor,
    onPrimary = Color.White,
    onSecondary = NoteText,
    onBackground = NoteText,
    onSurface = NoteText,
    error = DarkRed,
    onError = Color.White
)

private val LightColorPalette = lightColors(
    primary = LightBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White,
    surface = LightNoteColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = DarkRed,
    onError = Color.White

)

@Composable
fun OgranizerTusurTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette

    } else {
        LightColorPalette

    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
    if (darkTheme){
        ThemeStatusObj.provide(true)
    }else{
        ThemeStatusObj.provide(false)
    }
}