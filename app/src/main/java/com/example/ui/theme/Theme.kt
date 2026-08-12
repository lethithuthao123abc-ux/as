package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MedicalPrimaryDark,
    onPrimary = Color.Black,
    secondary = MedicalSecondaryDark,
    onSecondary = Color.Black,
    tertiary = MedicalWarningAmber,
    error = MedicalEmergencyRed,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = MedicalPrimaryLight,
    onPrimary = Color.White,
    secondary = MedicalSecondaryLight,
    onSecondary = Color.White,
    tertiary = MedicalWarningAmber,
    error = MedicalEmergencyRed,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface
)

@Composable
fun StrokeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep medical theme consistent across devices
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
