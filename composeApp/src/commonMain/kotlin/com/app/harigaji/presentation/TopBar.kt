package com.app.harigaji.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.theme.SpacerHorizontalMedium
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberInnerVerticalPaddingSmall
import com.app.harigaji.theme.rememberOuterHorizontalPaddingMedium
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeMedium
import com.app.harigaji.theme.rememberTextSizeExtraLarge
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    paddingValues: PaddingValues =PaddingValues(0.dp),
    title: String,
    modifier: Modifier = Modifier,
    onLeadingClick: (() -> Unit)? = null,
    onTrailingClick: (() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    backIconRes: DrawableResource? = Res.drawable.ic_curve_back,
    backIconVector: ImageVector? = null,
    trailingIconVector: ImageVector? = Icons.Default.Search,
    trailingIconRes: DrawableResource? = null,
    backIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingIconTint: Color = MaterialTheme.colorScheme.onSurface,
    backButtonBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backButtonBackgroundColor: Color = MaterialTheme.colorScheme.background,
    trailingButtonBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingButtonBackgroundColor: Color = Color.Transparent,
    titleFontSize: TextUnit = rememberTextSizeExtraLarge(),
    backButtonSize: Dp = rememberSizeExtraLarge(),
    trailingButtonSize: Dp = rememberSizeExtraLarge(),
    backIconSize: Dp = rememberSizeMedium(),
    trailingIconSize: Dp = rememberSizeMedium(),
    horizontalPadding: Dp = rememberOuterHorizontalPaddingMedium(),
    useSystemBarInsets: Boolean = true
) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = rememberInnerVerticalPaddingSmall())
                    .padding(top = paddingValues.calculateTopPadding())

            ) {
                // Back Button - only show if onBackClick is provided
                if (onLeadingClick != null) {
                    OutlinedIconButton(
                        border = BorderStroke(1.dp, backButtonBorderColor),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = backButtonBackgroundColor
                        ),
                        onClick = onLeadingClick,
                        modifier = Modifier
                            .size(backButtonSize)
                    ) {
                        when {
                            backIconVector != null -> {
                                Icon(
                                    imageVector = backIconVector,
                                    contentDescription = "Back",
                                    modifier = Modifier.size(backIconSize),
                                    tint = backIconTint
                                )
                            }
                            backIconRes != null -> {
                                Icon(
                                    painter = painterResource(backIconRes),
                                    contentDescription = "Back",
                                    modifier = Modifier.size(backIconSize),
                                    tint = backIconTint
                                )
                            }
                        }
                    }

                    SpacerHorizontalMedium()
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = titleColor,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = titleFontSize
                )

                if (onTrailingClick != null) {
                    OutlinedIconButton(
                        onClick = onTrailingClick,
                        modifier = Modifier
                            .size(trailingButtonSize),
                        border = BorderStroke(1.dp, trailingButtonBorderColor),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = trailingButtonBackgroundColor
                        )
                    ) {
                        when {
                            trailingIconVector != null -> {
                                Icon(
                                    imageVector = trailingIconVector,
                                    contentDescription = "Action",
                                    tint = trailingIconTint,
                                    modifier = Modifier.size(trailingIconSize)
                                )
                            }
                            trailingIconRes != null -> {
                                Icon(
                                    painter = painterResource(trailingIconRes),
                                    contentDescription = "Action",
                                    tint = trailingIconTint,
                                    modifier = Modifier.size(trailingIconSize)
                                )
                            }
                        }
                    }
                }
            }
}

// Preview Examples
@Preview
@Composable
private fun GenericTopAppBarPreview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            // With back button and trailing icon
            MyTopBar(
                title = "History",
                onLeadingClick = {},
                onTrailingClick = {},
                trailingIconVector = Icons.Default.Search
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Only title, no buttons
            MyTopBar(
                title = "Profile"
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyTopBar(
                title = "Settings",
                onLeadingClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyTopBar(
                title = "Notifications",
                onTrailingClick = {},
                trailingIconVector = Icons.Default.MoreVert
            )

            SpacerVerticalMedium()

            MyBottomNav(
                text = "Next",
                onClick = {}
            )
        }
    }
}


@Composable
fun MyBottomNav(
    text: String,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(bottom = 0.dp),
    shape: Shape = CircleShape,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null // ✅ null-safe callback
) {
    Button(
        onClick = { onClick?.invoke() }, // ✅ null-safe trigger
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = shape,
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}