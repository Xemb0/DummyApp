package com.app.harigaji.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.Urbanist
import org.jetbrains.compose.resources.Font

val myFontFamily @Composable get() = FontFamily(
    Font(
        resource = Res.font.Urbanist,
        weight = FontWeight.Bold
    )
)

val Inter @Composable get() = FontFamily(
    Font(
        resource = Res.font.Urbanist,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.Urbanist,
        weight = FontWeight.Medium
    ),
)

val Typography: Typography @Composable get() = Typography(
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp
    ),
    titleSmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),
)