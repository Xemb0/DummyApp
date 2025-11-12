package com.app.harigaji.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.presentation.tabs.KpiData
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PrivacyPolicyRoot(
    paddingValues: PaddingValues,
    aboutViewModel: AboutViewModel = koinViewModel(),
    onPrevious: () -> Unit = {}
) {
    val policyState by aboutViewModel.policyState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        aboutViewModel.loadPrivacyPolicy()

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

    PrivacyPolicyScreen(
        paddingValues = paddingValues,
        sections = policyState.sections,
        isLoading = policyState.isLoading,
        error = policyState.error,
        onPrevious = onPrevious
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    paddingValues: PaddingValues,
    sections: List<KpiData> = emptyList(),
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
                paddingValues = paddingValues,
                title = "Privacy Policy",
                onLeadingClick = onPrevious,
                modifier = Modifier.padding(horizontal =outerHorizontalPadding, vertical = outerVerticalPadding )
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
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
                            .padding(horizontal = 24.dp, vertical = 0.dp)
                    ) {
                        items(sections) { section ->
                            PolicySectionItem(
                                title = section.title,
                                content = section.content
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PolicySectionItem(
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
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            lineHeight = 22.sp
        )
    }
}