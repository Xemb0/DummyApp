package com.app.harigaji.presentation.uiconfig

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.harigaji.core.nativefetures.platformBlur
import com.app.harigaji.core.uiconfig.UiConfigEntity
import com.app.harigaji.core.uiconfig.UiConfigViewModel
import com.app.harigaji.core.uiconfig.ColorPresets

@Composable
fun FloatingUiControlPanel(
    viewModel: UiConfigViewModel,
    modifier: Modifier = Modifier
) {
    val uiConfig by viewModel.uiConfig.collectAsState()
    val isVisible = viewModel.isControlPanelVisible
    
    val currentConfig = uiConfig ?: UiConfigEntity()
    
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            // Glassmorphic Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.85f)
                    .align(Alignment.Center)
                    .platformBlur(radius = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UI Configuration",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        IconButton(onClick = { viewModel.toggleControlPanel() }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    // Color Presets Section
                    ConfigSection(
                        title = "Color Presets",
                        icon = Icons.Default.ColorLens
                    ) {
                        val presets = ColorPresets.getAllPresets()
                        presets.forEach { (name, preset) ->
                            PresetButton(
                                name = name,
                                preset = preset,
                                isSelected = currentConfig.primaryColor == preset.primaryColor &&
                                        currentConfig.secondaryColor == preset.secondaryColor,
                                onApply = {
                                    viewModel.updateColors(preset)
                                }
                            )
                        }
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    // Colors Section
                    ConfigSection(
                        title = "Custom Colors",
                        icon = Icons.Default.Palette
                    ) {
                        ColorSlider(
                            label = "Primary",
                            value = currentConfig.primaryColor,
                            onValueChange = { newValue ->
                                viewModel.updateColors(currentConfig.copy(primaryColor = newValue))
                            }
                        )
                        ColorSlider(
                            label = "Secondary",
                            value = currentConfig.secondaryColor,
                            onValueChange = { newValue ->
                                viewModel.updateColors(currentConfig.copy(secondaryColor = newValue))
                            }
                        )
                        ColorSlider(
                            label = "Surface",
                            value = currentConfig.surfaceColor,
                            onValueChange = { newValue ->
                                viewModel.updateColors(currentConfig.copy(surfaceColor = newValue))
                            }
                        )
                        ColorSlider(
                            label = "Background",
                            value = currentConfig.backgroundColor,
                            onValueChange = { newValue ->
                                viewModel.updateColors(currentConfig.copy(backgroundColor = newValue))
                            }
                        )
                        ColorSlider(
                            label = "On Surface",
                            value = currentConfig.onSurfaceColor,
                            onValueChange = { newValue ->
                                viewModel.updateColors(currentConfig.copy(onSurfaceColor = newValue))
                            }
                        )
                    }
                    
                    // Padding Section
                    ConfigSection(
                        title = "Padding",
                        icon = Icons.Default.FormatIndentIncrease
                    ) {
                        SizeSlider(
                            label = "Small",
                            value = currentConfig.paddingSmall,
                            range = 0f..32f,
                            onValueChange = { newValue ->
                                viewModel.updatePadding(currentConfig.copy(paddingSmall = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Medium",
                            value = currentConfig.paddingMedium,
                            range = 0f..32f,
                            onValueChange = { newValue ->
                                viewModel.updatePadding(currentConfig.copy(paddingMedium = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Large",
                            value = currentConfig.paddingLarge,
                            range = 0f..48f,
                            onValueChange = { newValue ->
                                viewModel.updatePadding(currentConfig.copy(paddingLarge = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Extra Large",
                            value = currentConfig.paddingExtraLarge,
                            range = 0f..64f,
                            onValueChange = { newValue ->
                                viewModel.updatePadding(currentConfig.copy(paddingExtraLarge = newValue))
                            }
                        )
                    }
                    
                    // Icon Sizes Section
                    ConfigSection(
                        title = "Icon Sizes",
                        icon = Icons.Default.Image
                    ) {
                        SizeSlider(
                            label = "Small",
                            value = currentConfig.iconSizeSmall,
                            range = 8f..64f,
                            onValueChange = { newValue ->
                                viewModel.updateIconSizes(currentConfig.copy(iconSizeSmall = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Medium",
                            value = currentConfig.iconSizeMedium,
                            range = 8f..64f,
                            onValueChange = { newValue ->
                                viewModel.updateIconSizes(currentConfig.copy(iconSizeMedium = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Large",
                            value = currentConfig.iconSizeLarge,
                            range = 8f..128f,
                            onValueChange = { newValue ->
                                viewModel.updateIconSizes(currentConfig.copy(iconSizeLarge = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Extra Large",
                            value = currentConfig.iconSizeExtraLarge,
                            range = 8f..128f,
                            onValueChange = { newValue ->
                                viewModel.updateIconSizes(currentConfig.copy(iconSizeExtraLarge = newValue))
                            }
                        )
                    }
                    
                    // Text Sizes Section
                    ConfigSection(
                        title = "Text Sizes",
                        icon = Icons.Default.TextFields
                    ) {
                        SizeSlider(
                            label = "Small",
                            value = currentConfig.textSizeSmall,
                            range = 8f..24f,
                            onValueChange = { newValue ->
                                viewModel.updateTextSizes(currentConfig.copy(textSizeSmall = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Medium",
                            value = currentConfig.textSizeMedium,
                            range = 8f..24f,
                            onValueChange = { newValue ->
                                viewModel.updateTextSizes(currentConfig.copy(textSizeMedium = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Large",
                            value = currentConfig.textSizeLarge,
                            range = 8f..32f,
                            onValueChange = { newValue ->
                                viewModel.updateTextSizes(currentConfig.copy(textSizeLarge = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Title",
                            value = currentConfig.textSizeTitle,
                            range = 12f..48f,
                            onValueChange = { newValue ->
                                viewModel.updateTextSizes(currentConfig.copy(textSizeTitle = newValue))
                            }
                        )
                        SizeSlider(
                            label = "Headline",
                            value = currentConfig.textSizeHeadline,
                            range = 16f..64f,
                            onValueChange = { newValue ->
                                viewModel.updateTextSizes(currentConfig.copy(textSizeHeadline = newValue))
                            }
                        )
                    }
                    
                    // Current Configuration Display
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Current Configuration",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ConfigInfoCard(currentConfig)
                }
            }
        }
    }
}

@Composable
fun ConfigSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        content()
    }
}

@Composable
fun ColorSlider(
    label: String,
    value: Long,
    onValueChange: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(value))
            )
        }
        
        // RGB Sliders
        val color = Color(value)
        val red = (color.red * 255).toInt()
        val green = (color.green * 255).toInt()
        val blue = (color.blue * 255).toInt()
        
        Text(
            text = "R: $red",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Slider(
            value = red.toFloat(),
            onValueChange = { newRed ->
                val newColor = Color(
                    red = newRed / 255f,
                    green = color.green,
                    blue = color.blue,
                    alpha = 1f
                )
                onValueChange(newColor.value.toLong())
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
        
        Text(
            text = "G: $green",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Slider(
            value = green.toFloat(),
            onValueChange = { newGreen ->
                val newColor = Color(
                    red = color.red,
                    green = newGreen / 255f,
                    blue = color.blue,
                    alpha = 1f
                )
                onValueChange(newColor.value.toLong())
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
        
        Text(
            text = "B: $blue",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Slider(
            value = blue.toFloat(),
            onValueChange = { newBlue ->
                val newColor = Color(
                    red = color.red,
                    green = color.green,
                    blue = newBlue / 255f,
                    alpha = 1f
                )
                onValueChange(newColor.value.toLong())
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SizeSlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${value.toInt()}dp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ConfigInfoCard(config: UiConfigEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            ConfigInfoRow("Primary Color", "#${config.primaryColor.toString(16).uppercase()}")
            ConfigInfoRow("Padding Large", "${config.paddingLarge.toInt()}dp")
            ConfigInfoRow("Icon Size Medium", "${config.iconSizeMedium.toInt()}dp")
            ConfigInfoRow("Text Size Large", "${config.textSizeLarge.toInt()}sp")
        }
    }
}

@Composable
fun PresetButton(
    name: String,
    preset: UiConfigEntity,
    isSelected: Boolean,
    onApply: () -> Unit
) {
    Button(
        onClick = onApply,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceContainerLowest
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            
            // Color preview chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(preset.primaryColor))
                )
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(preset.secondaryColor))
                )
            }
        }
    }
}

@Composable
fun ConfigInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

