package com.app.harigaji.core.uiconfig

/**
 * Predefined color scheme presets for quick application
 */
object ColorPresets {
    
    // Preset 1: Default Purple & Green (Original)
    val defaultPreset = UiConfigEntity(
        id = 1,
        primaryColor = 0xFF6200EE, // Purple
        secondaryColor = 0xFF2ECA83, // Green
        surfaceColor = 0xFFFFFFFF, // White
        surfaceLowestColor = 0xFFF5F5F5, // Light Gray
        backgroundColor = 0xFFF1F2F3, // Light Background
        onSurfaceColor = 0xFF254B2B, // Dark Green
        onSurfaceVariantColor = 0xFF186745, // Medium Green
        secondaryContainerColor = 0xFF208A5C // Dark Green
    )
    
    // Preset 2: Blue & Teal (Ocean Theme)
    val oceanPreset = UiConfigEntity(
        id = 1,
        primaryColor = 0xFF1976D2, // Blue
        secondaryColor = 0xFF00BCD4, // Teal
        surfaceColor = 0xFFFFFFFF, // White
        surfaceLowestColor = 0xFFE3F2FD, // Light Blue
        backgroundColor = 0xFFF5F9FC, // Very Light Blue
        onSurfaceColor = 0xFF0D47A1, // Dark Blue
        onSurfaceVariantColor = 0xFF0277BD, // Medium Blue
        secondaryContainerColor = 0xFF0097A7 // Dark Teal
    )
    
    // Preset 3: Orange & Amber (Sunset Theme)
    val sunsetPreset = UiConfigEntity(
        id = 1,
        primaryColor = 0xFFE65100, // Deep Orange
        secondaryColor = 0xFFFFB300, // Amber
        surfaceColor = 0xFFFFFFFF, // White
        surfaceLowestColor = 0xFFFFF3E0, // Light Orange
        backgroundColor = 0xFFFFF8F0, // Very Light Orange
        onSurfaceColor = 0xFFBF360C, // Dark Orange
        onSurfaceVariantColor = 0xFFE64A19, // Medium Orange
        secondaryContainerColor = 0xFFFF8F00 // Dark Amber
    )
    
    // Preset 4: Indigo & Pink (Modern Theme)
    val modernPreset = UiConfigEntity(
        id = 1,
        primaryColor = 0xFF3F51B5, // Indigo
        secondaryColor = 0xFFE91E63, // Pink
        surfaceColor = 0xFFFFFFFF, // White
        surfaceLowestColor = 0xFFF3E5F5, // Light Purple
        backgroundColor = 0xFFFAF5FF, // Very Light Purple
        onSurfaceColor = 0xFF1A237E, // Dark Indigo
        onSurfaceVariantColor = 0xFF303F9F, // Medium Indigo
        secondaryContainerColor = 0xFFC2185B // Dark Pink
    )
    
    fun getAllPresets(): List<Pair<String, UiConfigEntity>> {
        return listOf(
            "Default (Purple & Green)" to defaultPreset,
            "Ocean (Blue & Teal)" to oceanPreset,
            "Sunset (Orange & Amber)" to sunsetPreset,
            "Modern (Indigo & Pink)" to modernPreset
        )
    }
}

