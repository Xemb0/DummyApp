package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Article
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.app.harigaji.presentation.ProfileMenuItem
import com.app.harigaji.presentation.popups.LogoutDialog
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_exit
import harigaji.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import kotlin.math.max

// Profile Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onProfileClick: () -> Unit,
    onForgetPassClick: () -> Unit,
    onUpdatePinClick: () -> Unit,
    onLanguageClick: () -> Unit,
    onClearCacheClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsConditionsClick: () -> Unit,
    onArticleClick: () -> Unit,
) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState()
    )

    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("jpg", "png")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { platformFile ->
        showFilePicker = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .then(if (showLogoutDialog) Modifier.blur(8.dp) else Modifier),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 8.dp,
                    ),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(
                        bottomStart = max(24.dp.value, (48 - (36 * scrollBehavior.state.collapsedFraction))).dp,
                        bottomEnd = max(24.dp.value, (48 - (36 * scrollBehavior.state.collapsedFraction))).dp
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TopAppBar(
                            scrollBehavior = scrollBehavior,
                            windowInsets = WindowInsets
                                .systemBars
                                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent,
                            ),
                            title = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Profile",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset( y = (-6).dp)
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Image(
                                painter = painterResource(Res.drawable.ic_profile),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(84.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = "Arlene McCoy",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "@arlene22",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        ) { pv ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Personal Info",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProfileMenuItem("Profile", Icons.Default.Person, onClick = onProfileClick)
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileMenuItem("Articles", Icons.Default.Article, onClick = onArticleClick)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Security Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Security",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProfileMenuItem("Change Password", Icons.Default.Lock, onClick = onForgetPassClick)
                    Divider()
                    ProfileMenuItem("Forgot Password", Icons.Default.Lock, onClick = onForgetPassClick)
                    Divider()
                    ProfileMenuItem("Update PIN", Icons.Default.Pin, onClick = onUpdatePinClick)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // General Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "General",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProfileMenuItem("Language", Icons.Default.Language, onClick = onLanguageClick)
                    Divider()
                    ProfileMenuItem(
                        "Clear Cache",
                        Icons.Default.Delete,
                        showBadge = true,
                        badgeText = "48 MB",
                        onClick = onClearCacheClick
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // About Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "About",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProfileMenuItem("Privacy Policy", Icons.Default.PrivacyTip, onClick = onPrivacyPolicyClick)
                    Divider()
                    ProfileMenuItem("Terms and Conditions", Icons.Default.Description, onClick = onTermsConditionsClick)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(CircleShape)
                        .clickable(onClick = {
                            showLogoutDialog = true
                        })
                        .padding(vertical = 8.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Log Out",
                        color = Color(0xFF4CAF50),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(Res.drawable.ic_exit),
                        contentDescription = "Logout Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(240.dp))
            }
        }

        if (showLogoutDialog) {
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                    onLogout()
                }
            )
        }
    }
}