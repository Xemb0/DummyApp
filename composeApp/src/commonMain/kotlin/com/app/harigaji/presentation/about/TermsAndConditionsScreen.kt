package com.app.harigaji.presentation.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.presentation.ScreenHeader
import com.app.harigaji.presentation.tabs.KpiData
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(
    paddingValues: PaddingValues,
    onPrevious: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ScreenHeader(
                title = "Terms & Conditions",
                onBackClick = onPrevious,
                paddingValues = paddingValues
            )
        },
    ) { pv ->

        val listTermsSection = listOf(
            KpiData(
                label = "1. Acceptance of Terms",
                value = "By accessing and using this application, you accept and agree to be bound by the terms and provision of this agreement."
            ),
            KpiData(
                label = "2. Use License",
                value = "Permission is granted to temporarily access the materials on our app for personal, non-commercial transitory viewing only."
            ),
            KpiData(
                label = "3. User Account",
                value = "You are responsible for maintaining the confidentiality of your account and password and for restricting access to your device."
            ),
            KpiData(
                label = "4. Prohibited Uses",
                value = "You may not use our app for any illegal or unauthorized purpose. You must not violate any laws in your jurisdiction."
            ),
            KpiData(
                label = "5. Intellectual Property",
                value = "All content, trademarks, and data on this app, including software, databases, text, graphics, icons, and hyperlinks are the property of or licensed to us."
            ),
            KpiData(
                label = "6. Limitation of Liability",
                value = "We shall not be liable for any indirect, incidental, special, consequential or punitive damages resulting from your use of the app."
            ),
            KpiData(
                label = "7. Termination",
                value = "We may terminate or suspend your account and access to the app immediately, without prior notice, for any breach of these Terms."
            ),
            KpiData(
                label = "8. Modifications to Service",
                value = "We reserve the right to modify or discontinue the service at any time without notice. We shall not be liable to you for any modification or discontinuation."
            ),
            KpiData(
                label = "9. Governing Law",
                value = "These terms shall be governed and construed in accordance with the laws of our jurisdiction, without regard to its conflict of law provisions."
            ),
            KpiData(
                label = "10. Changes to Terms",
                value = "We reserve the right to update or change our Terms and Conditions at any time. Continued use of the app constitutes acceptance of those changes."
            )
        )

        Box(
            modifier = Modifier.padding(pv)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 0.dp)
            ) {
                items(listTermsSection) {
                    TermsSection(
                        title = it.label,
                        content = it.value
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