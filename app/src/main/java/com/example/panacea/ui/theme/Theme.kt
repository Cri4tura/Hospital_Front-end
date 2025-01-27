package com.example.panacea.ui.theme


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFF1A73E8),  // Azul fuerte (confianza y profesionalismo)
//    secondary = Color(0xFF4CAF50),
//    tertiary = Color(0xFF8BC34A),
//    onPrimary = Color.Black,
//    onSecondary = Color.Black,
//    onTertiary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White
//)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3B9E62),  // Verde profundo (nuevo color principal)
    secondary = Color(0xFF1A6F46),
    tertiary = Color(0xFF66BB6A),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3B9E62),  // Verde profundo (nuevo color principal)
    secondary = Color(0xFF2E7D56),
    tertiary = Color(0xFF81C784),
    background = Color(0xFFF0F4F3),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

//private val LightColorScheme = lightColorScheme(
//    primary = Color(0xFF1A73E8),  // Azul fuerte
//    secondary = Color(0xFF388E3C),  // Verde oscuro
//    tertiary = Color(0xFF76FF03),  // Verde claro
//    background = Color(0xFFF5F5F5),  // Gris suave para un fondo limpio
//    surface = Color(0xFFFFFFFF),  // Blanco para superficies
//    onPrimary = Color.White,  // Texto blanco sobre azul
//    onSecondary = Color.White,  // Texto blanco sobre verde
//    onTertiary = Color.Black,  // Texto negro sobre verde claro
//    onBackground = Color.Black,  // Texto negro sobre fondo gris
//    onSurface = Color.Black  // Texto negro sobre superficies blancas
//)
/*
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

 */

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HospitalFrontendTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
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