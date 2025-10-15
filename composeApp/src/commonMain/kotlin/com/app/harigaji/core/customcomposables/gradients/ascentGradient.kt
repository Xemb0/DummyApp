package com.app.harigaji.core.customcomposables.gradients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.theme.Primary
import com.app.harigaji.theme.Secondary
import com.app.harigaji.theme.SecondaryExtraDark
import com.app.harigaji.theme.Tertiary
import com.app.harigaji.theme.TertiaryExtraDark
import org.jetbrains.compose.ui.tooling.preview.Preview

// Reusable gradient brushes
val ascentGradient = Brush.linearGradient(
    colors = listOf(Tertiary, TertiaryExtraDark)  // Use your color variables here
)

val grayGradient = Brush.linearGradient(
    colors = listOf(Color.Gray, Color.Gray.copy(alpha = .7f))  // Use your color variables here
)
val ascentLightGradient = Brush.linearGradient(
    colors = listOf(Tertiary.copy(alpha = 0.1f), TertiaryExtraDark.copy(alpha = 0.1f))  // Use your color variables here
)
val transparentGradient = Brush.linearGradient(
    colors = listOf(Color.Transparent, Color.Transparent)  // Use your color variables here
)




val SecondaryGradient = Brush.linearGradient(
    colors = listOf(Secondary, Secondary)
)
val verticalSecondaryGradient = Brush.verticalGradient(
    colors = listOf(SecondaryExtraDark,Secondary )
)


val SecondaryGradientDark = Brush.horizontalGradient(
    colors = listOf(Primary, SecondaryExtraDark)
)

val SecondaryGradientDarkExtra = Brush.horizontalGradient(
    colors = listOf(SecondaryExtraDark, Secondary)
)




val SecondaryGradientHorizontal = Brush.horizontalGradient(
    colors = listOf(Secondary, Secondary)
)

val reverseSecondaryGradient = Brush.linearGradient(
    colors = listOf(Secondary, Secondary)  // Use your color variables here
)
val reverseSecondaryGradientDark = Brush.horizontalGradient(
    colors = listOf(SecondaryExtraDark, Primary)  // Use your color variables here
)
val lightSecondaryGradient = Brush.horizontalGradient(
    colors = listOf(Secondary.copy(alpha = 0.7f), Secondary.copy(alpha = 0.7f))  // Use your color variables here
)

val darkSecondaryGradient = Brush.horizontalGradient(
    colors = listOf(SecondaryExtraDark.copy(alpha = 1f), Secondary)
)
val lightSecondaryGradientVertical = Brush.verticalGradient(
    colors = listOf(Secondary.copy(alpha = 0.7f), Secondary.copy(alpha = 0.7f))  // Use your color variables here
)
val bgGradient = Brush.linearGradient(
    colors = listOf(Secondary.copy(alpha = 0.1f), Secondary.copy(alpha = 0.1f))  // Use your color variables here
)

// Additional gradients
val sunsetGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFFFA726), Color(0xFFEF5350))
)

val oceanGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF42A5F5), Color(0xFF29B6F6))
)

val forestGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF66BB6A), Color(0xFF388E3C))
)

val twilightGradient = Brush.radialGradient(
    colors = listOf(Color(0xFF8E24AA), Color(0xFF5E35B1)),
    center = Offset(0f, 0f),
    radius = 500f
)

// Vibrant gradient for a dynamic UI
val sunriseGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFFFC371), Color(0xFFFF5F6D))  // Peachy to red tones
)

// Cool and sleek gradient for professional themes
val iceGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF4FC3F7), Color(0xFF81D4FA))  // Light blue shades
)

// Fresh green for nature-related themes
val mintGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFB2FF59), Color(0xFF76FF03))  // Light to bright green
)

// Bold and deep for striking visuals
val royalGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF512DA8), Color(0xFF673AB7))  // Dark purple shades
)

// Smooth and subtle for background elements
val cloudGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFE0F7FA), Color(0xFFECEFF1))  // Light teal to pale gray
)

@Preview(heightDp = 8000)
@Composable
fun GradientDemo() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Display different gradients
        GradientBox("Ascent Gradient", ascentGradient)
        GradientBox("Gray Gradient", grayGradient)
        GradientBox("Ascent Light Gradient", ascentLightGradient)
        GradientBox("Secondary Gradient", SecondaryGradient)
        GradientBox("Reverse Secondary Gradient", reverseSecondaryGradient)
        GradientBox("Sunset Gradient", sunsetGradient)
        GradientBox("Ocean Gradient", oceanGradient)
        GradientBox("Forest Gradient", forestGradient)
        GradientBox("Twilight Gradient", twilightGradient)
        GradientBox("Sunrise Gradient", sunriseGradient)
        GradientBox("Ice Gradient", iceGradient)
        GradientBox("Mint Gradient", mintGradient)
        GradientBox("Royal Gradient", royalGradient)
        GradientBox("secedory gradient dark", SecondaryGradientDark)

    }
}

@Composable
fun GradientBox(label: String, brush: Brush) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(brush)
            .padding(8.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}