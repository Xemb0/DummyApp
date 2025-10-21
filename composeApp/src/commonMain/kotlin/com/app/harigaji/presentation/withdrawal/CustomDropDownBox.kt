package com.app.harigaji.presentation.withdrawal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomDropDownBox(
    color: Color = Color.Transparent,
    selectedReason: String,
    reasonError: Boolean,
    errorString: String? = null,
    dropDownList: List<String>? = null,
    tintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    label: String? = "Reason for route default",
    onReasonSelected: (String) -> Unit,
    isEnable: Boolean = true,
) {
    Column(Modifier.fillMaxWidth()) {
        // Label
        Text(
            text = label ?: "Reason for route default",
            color = tintColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        // Dropdown
        DropDown(
            options = dropDownList ?: listOf("Please select"),
            selectedOption = selectedReason,
            onOptionSelected = {
                onReasonSelected(it)
            },
            enabled = isEnable
        )

        // Error message
        if (reasonError) {
            Text(
                text = errorString ?: "Reason is required",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DropDown(
    enabled: Boolean,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedOption
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Debug logs
    LaunchedEffect(expanded) {
        println("DEBUG: Dropdown expanded state = $expanded")
        println("DEBUG: Options list = $options")
        println("DEBUG: Enabled = $enabled")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Dropdown Button
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            onClick = { if (enabled) expanded = !expanded },
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            enabled = enabled
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayText,
                    modifier = Modifier.weight(1f),
                    color = if (displayText.startsWith("Please")) {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    fontSize = 16.sp
                )
                Icon(
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                ),
            offset = DpOffset(0.dp, 4.dp)
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(
                        textColor = if (option == "Please select") {
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    ),
                    text = {
                        Text(
                            text = option,
                            fontWeight = if (option == "Please select") {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            },
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        if (option != "Please select") {
                            onOptionSelected(option)
                            expanded = false
                        }
                    }
                )

                // Add divider between items (except last item)
                if (index < options.size - 1) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}