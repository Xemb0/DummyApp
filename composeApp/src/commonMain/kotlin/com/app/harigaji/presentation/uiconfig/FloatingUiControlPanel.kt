package com.app.harigaji.presentation.uiconfig

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
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
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { viewModel.toggleControlPanel() }
        ) {
            // Glassmorphic Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .align(Alignment.Center)
                    .clickable(enabled = false) {} // Prevent click through
                    .platformBlur(radius = 25.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
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
                    
                    // Outline Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Layout Outlines",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Show red borders around layouts (like CSS outline)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                        Switch(
                            checked = viewModel.isOutlineModeEnabled,
                            onCheckedChange = { viewModel.toggleOutlineMode() }
                        )
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    // Reset to Default Button
                    Button(
                        onClick = {
                            viewModel.resetToDefault()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset to Default")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Color Presets Section - Only 3 presets
                    ConfigSection(
                        title = "Color Theme",
                        icon = Icons.Default.ColorLens
                    ) {
                        val presets = ColorPresets.getAllPresets().take(3) // Only first 3
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
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Inner Padding Horizontal - Single Multiplier
                    ConfigSection(
                        title = "Inner Padding Horizontal",
                        icon = Icons.Default.FormatIndentIncrease
                    ) {
                        MultiplierSlider(
                            label = "Inner Horizontal Padding",
                            value = currentConfig.innerPaddingHorizontalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(innerPaddingHorizontalMultiplier = newValue))
                            }
                        )
                        Text(
                            text = "Controls padding inside components (text-icon spacing)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Outer Padding Horizontal - Single Multiplier
                    ConfigSection(
                        title = "Outer Padding Horizontal",
                        icon = Icons.Default.FormatIndentIncrease
                    ) {
                        MultiplierSlider(
                            label = "Outer Horizontal Padding",
                            value = currentConfig.outerPaddingHorizontalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(outerPaddingHorizontalMultiplier = newValue))
                            }
                        )
                        Text(
                            text = "Controls padding between components and screen edges",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Inner Padding Vertical - Single Multiplier
                    ConfigSection(
                        title = "Inner Padding Vertical",
                        icon = Icons.Default.FormatIndentIncrease
                    ) {
                        MultiplierSlider(
                            label = "Inner Vertical Padding",
                            value = currentConfig.innerPaddingVerticalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(innerPaddingVerticalMultiplier = newValue))
                            }
                        )
                        Text(
                            text = "Controls padding inside components (vertical spacing)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Outer Padding Vertical - Single Multiplier
                    ConfigSection(
                        title = "Outer Padding Vertical",
                        icon = Icons.Default.FormatIndentIncrease
                    ) {
                        MultiplierSlider(
                            label = "Outer Vertical Padding",
                            value = currentConfig.outerPaddingVerticalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(outerPaddingVerticalMultiplier = newValue))
                            }
                        )
                        Text(
                            text = "Controls padding between components and screen edges",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Spacing Horizontal - Single Multiplier
                    ConfigSection(
                        title = "Spacing Horizontal",
                        icon = Icons.Default.SpaceBar
                    ) {
                        MultiplierSlider(
                            label = "Horizontal Spacing",
                            value = currentConfig.spacingHorizontalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(spacingHorizontalMultiplier = newValue))
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Spacing Vertical - Single Multiplier
                    ConfigSection(
                        title = "Spacing Vertical",
                        icon = Icons.Default.SpaceBar
                    ) {
                        MultiplierSlider(
                            label = "Vertical Spacing",
                            value = currentConfig.spacingVerticalMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(spacingVerticalMultiplier = newValue))
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Corner Radius - Single Multiplier
                    ConfigSection(
                        title = "Corner Radius",
                        icon = Icons.Default.CropFree
                    ) {
                        MultiplierSlider(
                            label = "Corner Radius",
                            value = currentConfig.cornerRadiusMultiplier,
                            range = 0.5f..2.0f,
                            onValueChange = { newValue ->
                                viewModel.updateConfig(currentConfig.copy(cornerRadiusMultiplier = newValue))
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
            .padding(vertical = 12.dp)
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
    // Use state to prevent glitches
    var currentColor by remember(value) { mutableStateOf(Color(value)) }
    
    LaunchedEffect(value) {
        currentColor = Color(value)
    }
    
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
                    .background(currentColor)
            )
        }
        
        // RGB Sliders - use currentColor state
        val red = (currentColor.red * 255).toInt()
        val green = (currentColor.green * 255).toInt()
        val blue = (currentColor.blue * 255).toInt()
        
        Text(
            text = "R: $red",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Slider(
            value = red.toFloat(),
            onValueChange = { newRed ->
                currentColor = Color(
                    red = newRed / 255f,
                    green = currentColor.green,
                    blue = currentColor.blue,
                    alpha = 1f
                )
                onValueChange(currentColor.value.toLong())
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
                currentColor = Color(
                    red = currentColor.red,
                    green = newGreen / 255f,
                    blue = currentColor.blue,
                    alpha = 1f
                )
                onValueChange(currentColor.value.toLong())
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
                currentColor = Color(
                    red = currentColor.red,
                    green = currentColor.green,
                    blue = newBlue / 255f,
                    alpha = 1f
                )
                onValueChange(currentColor.value.toLong())
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
fun MultiplierSlider(
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
                text = "${kotlin.math.round(value * 100) / 100f}x",
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
        Text(
            text = "1.0x = Default | Scales all ${label.lowercase()} proportionally",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp)
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
            ConfigInfoRow("Inner Padding H", "${kotlin.math.round(config.innerPaddingHorizontalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Outer Padding H", "${kotlin.math.round(config.outerPaddingHorizontalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Inner Padding V", "${kotlin.math.round(config.innerPaddingVerticalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Outer Padding V", "${kotlin.math.round(config.outerPaddingVerticalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Spacing H", "${kotlin.math.round(config.spacingHorizontalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Spacing V", "${kotlin.math.round(config.spacingVerticalMultiplier * 100) / 100f}x")
            ConfigInfoRow("Corner Radius", "${kotlin.math.round(config.cornerRadiusMultiplier * 100) / 100f}x")
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

