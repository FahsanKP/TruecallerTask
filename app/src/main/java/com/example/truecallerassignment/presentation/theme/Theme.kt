package com.example.truecallerassignment.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Truecaller-inspired color palette
private val TruecallerBlue = Color(0xFF0091FF)
private val TruecallerDarkBlue = Color(0xFF0066CC)

private val DarkColorScheme = darkColorScheme(
    primary = TruecallerBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1A2332),
    onPrimaryContainer = TruecallerBlue,

    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF1A2B2B),
    onSecondaryContainer = Color(0xFF03DAC5),

    tertiary = Color(0xFFBB86FC),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF2A1F32),
    onTertiaryContainer = Color(0xFFBB86FC),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFE0E0E0),

    error = Color(0xFFCF6679),
    onError = Color.Black,
    errorContainer = Color(0xFF3A1F1F),
    onErrorContainer = Color(0xFFFFB4AB)
)

private val LightColorScheme = lightColorScheme(
    primary = TruecallerBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6EAFF),
    onPrimaryContainer = TruecallerDarkBlue,

    secondary = Color(0xFF00BFA5),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD0F5F0),
    onSecondaryContainer = Color(0xFF006B5E),

    tertiary = Color(0xFF9C27B0),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF3E5F7),
    onTertiaryContainer = Color(0xFF4A148C),

    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1A1A1A),

    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color(0xFF424242),

    error = Color(0xFFB00020),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A)
)

@Composable
fun TrueCallerAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
