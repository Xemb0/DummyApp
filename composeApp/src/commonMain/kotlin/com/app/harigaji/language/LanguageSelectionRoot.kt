package com.app.harigaji.language

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.dialog.DialogConfig
import com.app.harigaji.dialog.DialogManager
import com.app.harigaji.dialog.DialogType
import com.app.harigaji.presentation.Language
import com.app.harigaji.presentation.MyBottomNav
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LanguageSelectionRoot(
    paddingValues: PaddingValues,
    languageViewModel: LanguageViewModel = koinViewModel(),
    currentLanguage: String = "English (US)",
    onPrevious: () -> Unit = {},
    onLanguageSaved: () -> Unit = {}
) {
    val languageState by languageViewModel.languageState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        languageViewModel.uiEvent.collect { event ->
            when (event) {
                is LanguageUiEvent.ShowSnackBar -> {
                    SnackBarEvent(
                        message = event.message,
                    )
                }
                is LanguageUiEvent.LanguageChanged -> {
                    DialogManager.showDialog(
                        DialogConfig(
                            type = DialogType.Success,
                            title = "Success",
                            message = "Language has been updated successfully.",
                            confirmText = "Continue",
                            onConfirm = {
                                onLanguageSaved()
                            }
                        )
                    )
                }
            }
        }
    }

    LanguageSelectionScreen(
        paddingValues = paddingValues,
        currentLanguage = currentLanguage,
        isLoading = languageState.isLoading,
        error = languageState.error,
        onPrevious = onPrevious,
        onSaveLanguage = { selectedLanguage ->
            languageViewModel.onLanguageSelected(selectedLanguage)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(
    paddingValues: PaddingValues,
    currentLanguage: String = "English (US)",
    isLoading: Boolean = false,
    error: String? = null,
    onPrevious: () -> Unit = {},
    onSaveLanguage: (String) -> Unit = {}
) {
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    var searchQuery by remember { mutableStateOf("") }

    val languages = listOf(
        Language(name = "English (US)", codeName = "en-US", flag = "US"),
        Language(name = "Italy", codeName = "it", flag = "IT"),
        Language(name = "France", codeName = "fr", flag = "FR"),
        Language(name = "Germany", codeName = "de", flag = "DE"),
        Language(name = "Japanese", codeName = "ja", flag = "JP"),
        Language(name = "Swedish", codeName = "sv", flag = "SE"),
        Language(name = "Russian", codeName = "ru", flag = "RU")
    )

    val filteredLanguages = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            languages
        } else {
            languages.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = "Language",
                onLeadingClick = onPrevious,
                modifier = Modifier.padding(horizontal = outerHorizontalPadding, vertical = outerVerticalPadding)
            )
        },
        bottomBar = {
            MyBottomNav(
                paddingValues = paddingValues,
                text = "Save",
                enabled = selectedLanguage != currentLanguage && !isLoading,
                modifier = Modifier.padding(horizontal = outerHorizontalPadding, vertical = outerVerticalPadding),
                        onClick = {
                    if (!isLoading) {
                        onSaveLanguage(selectedLanguage)
                    }
                }
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Show global error if exists
                error?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    placeholder = {
                        Text(
                            text = "Search",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Language List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredLanguages) { language ->
                        LanguageItem(
                            language = language.name,
                            isSelected = language.name == selectedLanguage,
                            enabled = !isLoading,
                            onClick = { selectedLanguage = language.name }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageItem(
    language: String,
    isSelected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = if (enabled) 1f else 0.5f
                )
            )

            Surface(
                modifier = Modifier.size(28.dp),
                shape = CircleShape,
                color = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                border = BorderStroke(
                    width = 2.dp,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    }
                )
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}