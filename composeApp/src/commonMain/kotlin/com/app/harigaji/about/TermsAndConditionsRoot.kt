package com.app.harigaji.about

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.utils.SnackBarEvent
import org.koin.compose.viewmodel.koinViewModel


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium



@Composable
fun TermsAndConditionsRoot(
    paddingValues: PaddingValues,
    aboutViewModel: AboutViewModel = koinViewModel(),
    onPrevious: () -> Unit = {}
) {
    val termsCondition by aboutViewModel.termsAndConditionsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        aboutViewModel.loadTermsAndConditions()

        aboutViewModel.uiEvent.collect { event ->
            when (event) {
                is PolicyUiEvent.ShowSnackBar -> {
                    SnackBarEvent(
                        message = event.message,
                    )
                }
            }
        }
    }

    TermsAndConditionsScreen(
        paddingValues = paddingValues,
        isLoading = termsCondition.isLoading,
        error = termsCondition.error,
        onPrevious = onPrevious,
        termsAndConditionsState = termsCondition,

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(
    paddingValues: PaddingValues,
    termsAndConditionsState: TermsAndConditionsState,
    isLoading: Boolean = false,
    error: String? = null,
    onPrevious: () -> Unit = {}

) {
    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MyTopBar(
                title = "Terms & Conditions",
                onLeadingClick = onPrevious,
                paddingValues = paddingValues,
                modifier = Modifier.padding(horizontal = outerHorizontalPadding, vertical = outerVerticalPadding)

            )
        },
    ) { pv ->

        Box(
            modifier = Modifier.padding(pv)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = error,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = outerHorizontalPadding)
                    ) {
                        items(termsAndConditionsState.termsAndConditions) {

                            SpacerVerticalMedium()

                            TermsSection(
                                title = it.title,
                                content = it.content
                            )
                        }

                        item {
                            SpacerVerticalLarge()
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun TermsSection(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = content,
            fontSize = 14.sp,
            color = Color(0xFF666666),
            lineHeight = 22.sp
        )
    }
}