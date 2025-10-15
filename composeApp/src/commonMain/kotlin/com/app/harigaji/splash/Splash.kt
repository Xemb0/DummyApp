package com.app.harigaji.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.customcomposables.ConvexBottomCard
import com.app.harigaji.core.customcomposables.gradients.darkSecondaryGradient
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_farward
import harigaji.composeapp.generated.resources.logo_app
import harigaji.composeapp.generated.resources.logo_splash
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SplashStartScreen(
    onGetStartedClick: () -> Unit = {}
) {
    var showTopLogo by remember { mutableStateOf(false) }
    var showMainLogo by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }
    var arcAnimationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        arcAnimationStarted = true
        delay(200)
        showMainLogo = true
        showTopLogo = true
        delay(300)
        showButton = true
    }

    val scrollState = remember { mutableStateOf(0) }
    val atTop by remember { derivedStateOf { scrollState.value < 20 } }

    val arcHeight by animateFloatAsState(
        targetValue = if (atTop && arcAnimationStarted) 150f else 0f,
        animationSpec = tween(2000),
        label = "arcHeightAnim"
    )
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 30000)
            )
        }

    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ConvexBottomCard(
                corners = 0.dp,
                elevation = 2.dp,
                arcHeight = arcHeight,
                paddingBottom = 0.dp,
                background = darkSecondaryGradient,
                modifier = Modifier.fillMaxHeight(.8f),
                color = MaterialTheme.colorScheme.secondary
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Top Logo
                    AnimatedVisibility(
                        visible = showTopLogo,
                        enter = fadeIn(
                            animationSpec = tween(1000)
                        ) + slideInVertically(
                            initialOffsetY = { -it / 2 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.logo_app),
                            contentDescription = "App Logo",
                            modifier = Modifier.padding(top = 32.dp)
                                .scale(scale.value)
                        )
                    }

                    // Main Logo
                    AnimatedVisibility(
                        visible = showMainLogo,
                        enter = fadeIn(
                            animationSpec = tween(1200)
                        ) + scaleIn(
                            initialScale = 0.7f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.logo_splash),
                            contentDescription = "Main Logo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(scale.value)
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }


                AnimatedVisibility(
                    modifier = Modifier.fillMaxSize(),
                    visible = showButton,
                    enter = fadeIn(
                        animationSpec = tween(1000)
                    ) + slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(CircleShape)
                                .background(darkSecondaryGradient)
                                .wrapContentSize(),
                            onClick = onGetStartedClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    "Get Started",
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    painter = painterResource(Res.drawable.ic_farward),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

        }
    }
}


@Composable
@Preview
fun SplashStartScreenPreview() {
    MaterialTheme {
        SplashStartScreen()
    }
}