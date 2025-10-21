package com.app.harigaji.presentation.about



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.ScreenHeader
import com.app.harigaji.presentation.tabs.KpiData
import com.app.harigaji.presentation.tabs.profile.CustomTextField
import com.app.harigaji.presentation.tabs.profile.OTPSection
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import harigaji.composeapp.generated.resources.ic_envelop
import harigaji.composeapp.generated.resources.ic_invalid
import harigaji.composeapp.generated.resources.ic_lock
import harigaji.composeapp.generated.resources.ic_profile
import io.github.ismoy.imagepickerkmp.domain.config.CameraCaptureConfig
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.config.PermissionAndConfirmationConfig
import io.github.ismoy.imagepickerkmp.domain.models.CompressionLevel
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.domain.models.MimeType
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun PrivacyPolicyScreen(
    paddingValues: PaddingValues,
    onPrevious: () -> Unit = {},
) {




    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {

        },
        topBar = {
            ScreenHeader(
                title = "Privacy Policy",
                onBackClick = onPrevious,
                paddingValues = paddingValues
            )
        },
    ) { pv ->

        val listPrivacySection  = listOf(
            KpiData(
                label = "1. Data Collection",
                value = "We collect personal information such as your name, email address, and usage data to improve our services."
            ),
            KpiData(
                label = "2. Data Usage",
                value = "Your data is used to personalize your experience, provide customer support, and send important updates."
            ),
            KpiData(
                label = "3. Data Sharing",
                value = "We do not share your personal information with third parties without your consent, except as required by law."
            ),
            KpiData(
                label = "4. Data Security",
                value = "We implement industry-standard security measures to protect your data from unauthorized access and breaches."
            ),
            KpiData(
                label = "5. User Rights",
                value = "You have the right to access, modify, or delete your personal information at any time by contacting our support team."
            ),
            KpiData(
                label = "6. Cookies",
                value = "We use cookies to enhance your browsing experience and gather analytics about site usage."
            ),
            KpiData(
                label = "7. Changes to This Policy",
                value = "We may update our Privacy Policy periodically. We will notify you of any significant changes via email or through our app."
            )
        )

        Box(

            modifier = Modifier
                .padding(pv)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 0.dp)
            ) {

                items(listPrivacySection){
                    PrivacySection(
                        title = it.label,
                        content = it.value
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item{
                Spacer(modifier = Modifier.height(20.dp))
            }
                }
        }
    }
}

@Composable
fun PrivacySection(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
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