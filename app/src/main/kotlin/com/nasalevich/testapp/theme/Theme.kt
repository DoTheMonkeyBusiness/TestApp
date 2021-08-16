package com.nasalevich.testapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nasalevich.testapp.theme.AppColor.Dark
import com.nasalevich.testapp.theme.AppColor.Light

private object AppColor {

    object Light {
        val SystemTeal = Color(0xff5AC8FA)
        val SystemBlue = Color(0xff007AFF)
        val SystemGray01 = Color(0xff8E8E93)
        val SystemGray06 = Color(0xffF2F2F7)
    }

    object Dark {
        val SystemTeal = Color(0xff64D2FF)
        val SystemBlue = Color(0xff0A84FF)
        val SystemGray01 = Color(0xff8E8E93)
        val SystemGray06 = Color(0xff1C1C1E)
        val DarkBackground = Color(0xff292929)
    }
}

private val lightColors = lightColors(
    primary = Light.SystemBlue,
    primaryVariant = Light.SystemTeal,
    onPrimary = Color.White,
    secondary = Light.SystemGray01,
    background = Light.SystemGray06,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

private val darkColors = darkColors(
    primary = Dark.SystemBlue,
    onPrimary = Color.White,
    primaryVariant = Dark.SystemTeal,
    secondary = Dark.SystemGray01,
    background = Dark.DarkBackground,
    onBackground = Color.White,
    surface = Dark.SystemGray06,
    onSurface = Color.White,
)

@Composable
fun AppTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) darkColors else lightColors

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        content = content,
        typography = Typography
    )
}
