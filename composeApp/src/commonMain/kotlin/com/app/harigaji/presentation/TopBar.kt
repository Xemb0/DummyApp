package com.app.harigaji.presentation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScreenHeader(
    title: String,
    onBackClick: () -> Unit,
    trailingIcon : @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    backIconRes: DrawableResource = Res.drawable.ic_curve_back,
    backIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backButtonBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backButtonBackgroundColor: Color = MaterialTheme.colorScheme.background,
    titleFontSize: Int = 24,
    backButtonSize: Dp = 48.dp,
    backIconSize: Dp = 24.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 8.dp,
    spaceBetween: Dp = 16.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(paddingValues)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(
            border = BorderStroke(1.dp, backButtonBorderColor),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = backButtonBackgroundColor
            ),
            onClick = onBackClick,
            modifier = Modifier.size(backButtonSize)
        ) {
            Icon(
                painter = painterResource(backIconRes),
                contentDescription = "Back",
                modifier = Modifier.size(backIconSize),
                tint = backIconTint
            )
        }

        Spacer(modifier = Modifier.width(spaceBetween))

        Text(
            text = title,
            fontSize = titleFontSize.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

// Simplified version with fewer parameters for common use cases
@Composable
fun SimpleScreenHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    ScreenHeader(
        title = title,
        onBackClick = onBackClick,
        modifier = modifier,
        paddingValues = paddingValues
    )
}