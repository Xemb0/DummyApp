package com.app.harigaji.navigation
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.zIndex
import com.app.harigaji.navigation.BottomNavIcon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

data class NavItem(
    val icon: ImageVector,
    val label: String,
    val hasNotification: Boolean = false
)

data class FabMenuItem(
    val icon: DrawableResource,
    val label: String,
    val color: Color
)

@Composable
fun CurvedBottomNavigation(
    leftItems: List<BottomNavIcon>,
    rightItems: List<BottomNavIcon>,
    fabMenuItems: List<FabMenuItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    onFabMenuItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(selectedIndex) }
    var isFabExpanded by remember { mutableStateOf(false) }

    // Continuous rotation for FAB background glow
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val glowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "glow_rotation"
    )

    // Pulsating glow effect
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    // FAB rotation animation - smooth and snappy
    val fabRotation by animateFloatAsState(
        targetValue = if (isFabExpanded) 135f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fab_rotation"
    )

    // FAB scale animation
    val fabScale by animateFloatAsState(
        targetValue = if (isFabExpanded) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "fab_scale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {

        // Shadow layer - same shape as the bar
        VectorShape(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-2).dp)
                .blur(2.dp)
                .alpha(0.2f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // Main bottom bar shape
        VectorShape(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        )

        // Navigation items layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left items
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                leftItems.forEachIndexed { index, item ->
                    NavItemContent(
                        bottomNavItem = item,
                        isSelected = selected == index,
                        onClick = {
                            selected = index
                            onItemSelected(index)
                            isFabExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(100.dp))

            // Right items
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rightItems.forEachIndexed { index, item ->
                    val actualIndex = leftItems.size + index
                    NavItemContent(
                        bottomNavItem = item,
                        isSelected = selected == actualIndex,
                        onClick = {
                            selected = actualIndex
                            onItemSelected(actualIndex)
                            isFabExpanded = false
                        }
                    )
                }
            }
        }

        // Center FAB with animated glow effect and menu items
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp),
            contentAlignment = Alignment.Center
        ) {
            // Floating menu items BEHIND the FAB (lower zIndex)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .zIndex(0f), // Behind FAB
                contentAlignment = Alignment.BottomCenter
            ) {
                fabMenuItems.forEachIndexed { index, menuItem ->
                    FloatingMenuItemDramatic(
                        item = menuItem,
                        index = index,
                        totalItems = fabMenuItems.size,
                        isVisible = isFabExpanded,
                        onClick = {
                            onFabMenuItemClick(menuItem.label)
                            isFabExpanded = false
                        }
                    )
                }
            }
            if(isFabExpanded){

            // Animated glow layers (in front of menu items but behind FAB)
            Canvas(
                modifier = Modifier
                    .size(90.dp)
                    .scale(if(isFabExpanded) glowScale else 1f)
                    .rotate(if(isFabExpanded) glowRotation else 0f)
                    .alpha(if (isFabExpanded) 0.7f else 0.4f)
                    .zIndex(1f)
            ) {
                val gradient = Brush.sweepGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF4CAF50).copy(alpha = 0.3f),
                        Color(0xFF81C784).copy(alpha = 0.5f),
                        Color.Transparent
                    )
                )
                drawCircle(brush = gradient)
            }

            }

            // FAB on top (highest zIndex)
            FloatingActionButton(
                onClick = { isFabExpanded = !isFabExpanded },
                containerColor = if (isFabExpanded)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.secondary,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = if(isFabExpanded) 32.dp else 12.dp,
                    pressedElevation = 16.dp
                ),
                modifier = Modifier
                    .size(60.dp)
                    .scale(fabScale)
                    .shadow(
                        elevation = 20.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.secondary,
                        spotColor = MaterialTheme.colorScheme.secondary
                    )
                    .zIndex(2f) // Highest, in front of everything
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(fabRotation)
                )
            }
        }
    }
}

@Composable
fun FloatingMenuItemDramatic(
    item: FabMenuItem,
    index: Int,
    totalItems: Int,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    val baseAngle = 90f
    val spreadAngle = 60f
    val angle = baseAngle + (index - (totalItems - 1) / 2f) * (spreadAngle / (totalItems - 1).coerceAtLeast(1))
    val radius = 120.dp.value

    val angleRad = (angle * PI / 180.0)
    val finalX = (radius * cos(angleRad)).dp
    val finalY = -(radius * sin(angleRad)).dp

    val startY = 0.dp
    val delay = index * 80

    val animatedX by animateDpAsState(
        targetValue = if (isVisible) finalX else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = 0.1.dp
        ),
        label = "menu_x_$index"
    )

    val animatedY by animateDpAsState(
        targetValue = if (isVisible) finalY else startY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = 0.1.dp
        ),
        label = "menu_y_$index"
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "menu_scale_$index"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "menu_alpha_$index"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else -90f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "menu_rotation_$index"
    )

    // Animated shadow elevation
    val shadowElevation by animateDpAsState(
        targetValue = if (isVisible) 16.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "shadow_elevation_$index"
    )

    Box(
        modifier = Modifier
            .offset(x = animatedX, y = animatedY)
            .size(65.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                rotationZ = rotation

                // Keep shadow circular by applying it at the graphics layer level
                clip = true
                shape = CircleShape

                // USE THE ANIMATED VALUE HERE instead of the conditional
                this.shadowElevation = shadowElevation.toPx()
                this.spotShadowColor = Color.Black.copy(alpha = 1f)
                this.ambientShadowColor = Color.Black.copy(alpha = 1f)
            }
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.fillMaxSize(),
            containerColor = item.color,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp,
                focusedElevation = 2.dp,
                hoveredElevation = 2.dp
            )
        ) {
            Icon(
                painter = painterResource( item.icon),
                contentDescription = item.label,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
// Alternative approach with Canvas for custom shadow
@Composable
fun FloatingMenuItemDramaticCustomShadow(
    item: FabMenuItem,
    index: Int,
    totalItems: Int,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    val baseAngle = 90f
    val spreadAngle = 60f
    val angle = baseAngle + (index - (totalItems - 1) / 2f) * (spreadAngle / (totalItems - 1).coerceAtLeast(1))
    val radius = 120.dp.value

    val angleRad = (angle * PI / 180.0)
    val finalX = (radius * cos(angleRad)).dp
    val finalY = -(radius * sin(angleRad)).dp

    val startY = 0.dp
    val delay = index * 80

    val animatedX by animateDpAsState(
        targetValue = if (isVisible) finalX else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = 0.1.dp
        ),
        label = "menu_x_$index"
    )

    val animatedY by animateDpAsState(
        targetValue = if (isVisible) finalY else startY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = 0.1.dp
        ),
        label = "menu_y_$index"
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "menu_scale_$index"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "menu_alpha_$index"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else -90f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "menu_rotation_$index"
    )

    Box(
        modifier = Modifier
            .offset(x = animatedX, y = animatedY)
            .size(65.dp)
    ) {
        // Custom circular shadow that stays perfect during animation
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = if (isVisible) 0.3f else 0f
                }
        ) {
            val shadowRadius = size.width / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Draw layered shadows for depth
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = shadowRadius * 1.1f
                ),
                radius = shadowRadius * 1.1f,
                center = center.copy(y = center.y + 3.dp.toPx())
            )
        }

        // Main button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                    rotationZ = rotation
                }
        ) {
            FloatingActionButton(
                onClick = onClick,
                shape = CircleShape,
                modifier = Modifier.fillMaxSize(),
                containerColor = item.color,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = item.label,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
@Composable
fun NavItemContent(
    bottomNavItem: BottomNavIcon,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Smooth scale animation
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    // Icon rotation on selection
    val rotation by animateFloatAsState(
        targetValue = if (isSelected) 360f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "rotation"
    )

    // Pulsating effect for selected item
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Color animation
    val iconAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.6f,
        animationSpec = tween(300),
        label = "alpha"
    )

    // Border animation
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 2.5.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "border"
    )

    Box(
        modifier = Modifier
            .size(64.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated background circle
        if (isSelected) {
            Canvas(
                modifier = Modifier
                    .size(60.dp)
                    .scale(pulse)
            ) {
                drawCircle(
                    color = Color(0xFF4CAF50).copy(alpha = 0.1f),
                    radius = size.minDimension / 2
                )
            }
        }

        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .scale(scale)
                    .graphicsLayer {
                        rotationZ = if (isSelected) rotation else 0f
                    }
            ) {
                val secondaryColor = MaterialTheme.colorScheme.secondary
                val surfaceColor = MaterialTheme.colorScheme.surface
                val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant

                Icon(
                    painter = if (isSelected) bottomNavItem.selectedIcon else bottomNavItem.unSelectedIcon,
                    contentDescription = bottomNavItem.title,
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    else
                        MaterialTheme.colorScheme.onBackground.copy(alpha = .4f),
                    modifier = Modifier
                        .clip(CircleShape)
                        // Outer glow effect (from perimeter inward)
                        .then(
                            if (isSelected) {
                                Modifier.drawBehind {
                                    // Multiple layers for glow effect
                                    drawCircle(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                secondaryColor.copy(alpha = 0.6f),
                                                secondaryColor.copy(alpha = 0.3f),
                                                Color.Transparent
                                            ),
                                            center = center,
                                            radius = size.minDimension / 1.5f
                                        )
                                    )
                                }
                            } else Modifier
                        )
                        // Background with radial gradient (variant approaching center)
                        .background(
                            brush = if (isSelected) {
                                Brush.radialGradient(
                                    colors = listOf(
                                        surfaceColor.copy(alpha = 1f), // Center
                                        secondaryColor.copy(alpha = 0.1f), // Middle
                                        surfaceVariantColor.copy(alpha = 0.7f)  // Edge
                                    ),
                                    radius = 150f
                                )
                            } else {
                                Brush.radialGradient(
                                    colors = listOf(
                                        surfaceColor.copy(alpha = 0.7f),
                                        surfaceColor.copy(alpha = 0.7f)
                                    )
                                )
                            },
                            shape = CircleShape
                        )
//                        .innerShadow(
//                            shape = CircleShape,
//                            shadow = Shadow(
//                                offset = DpOffset(0.dp, 0.dp),
//                                radius = .3.dp,
//                                spread = .5.dp,
//                                alpha = .5f,
//                                blendMode = BlendMode.Multiply,
//                                color = if (isSelected)
//                                    secondaryColor.copy(alpha = 0.6f)
//                                else Color.Black.copy(alpha = 0f)
//                            )
//                        )
                        .padding(10.dp)
                        .size(if (isSelected) 28.dp else 20.dp)
                )
            }

            // Animated notification badge
            if (bottomNavItem.badgeCount > 0 || bottomNavItem.hasNews) {
                val badgeScale by infiniteTransition.animateFloat(
                    initialValue = 0.9f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "badge_scale"
                )

                Box(
                    modifier = Modifier
                        .offset(x = 6.dp, y = (-2).dp)
                        .size(12.dp)
                        .scale(badgeScale)
                        .shadow(4.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFFFF5252))
                )
            }
        }
    }
}

@Composable
fun VectorShape(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val originalWidth = 430f
        val scale = canvasWidth / originalWidth
        val offsetY = -50f * scale
        val scaleX = scale
        val scaleY = scale

        val path = Path().apply {
            moveTo(183.62f * scaleX, 87.94f * scaleY + offsetY)
            cubicTo(
                169.08f * scaleX, 73.82f * scaleY + offsetY,
                153.28f * scaleX, 55.04f * scaleY + offsetY,
                133.01f * scaleX, 55.04f * scaleY + offsetY
            )
            lineTo(45.87f * scaleX, 55.04f * scaleY + offsetY)
            cubicTo(
                20.54f * scaleX, 55.04f * scaleY + offsetY,
                0f * scaleX, 75.58f * scaleY + offsetY,
                0f * scaleX, 100.91f * scaleY + offsetY
            )
            cubicTo(
                0f * scaleX, 126.24f * scaleY + offsetY,
                20.54f * scaleX, 146.77f * scaleY + offsetY,
                45.87f * scaleX, 146.77f * scaleY + offsetY
            )
            lineTo(384.13f * scaleX, 146.77f * scaleY + offsetY)
            cubicTo(
                409.46f * scaleX, 146.77f * scaleY + offsetY,
                430f * scaleX, 126.24f * scaleY + offsetY,
                430f * scaleX, 100.91f * scaleY + offsetY
            )
            cubicTo(
                430f * scaleX, 75.58f * scaleY + offsetY,
                409.46f * scaleX, 55.04f * scaleY + offsetY,
                384.13f * scaleX, 55.04f * scaleY + offsetY
            )
            lineTo(298.13f * scaleX, 55.04f * scaleY + offsetY)
            cubicTo(
                277.87f * scaleX, 55.04f * scaleY + offsetY,
                262.07f * scaleX, 73.82f * scaleY + offsetY,
                247.53f * scaleX, 87.94f * scaleY + offsetY
            )
            cubicTo(
                239.27f * scaleX, 95.97f * scaleY + offsetY,
                228f * scaleX, 100.91f * scaleY + offsetY,
                215.57f * scaleX, 100.91f * scaleY + offsetY
            )
            cubicTo(
                203.15f * scaleX, 100.91f * scaleY + offsetY,
                191.88f * scaleX, 95.97f * scaleY + offsetY,
                183.62f * scaleX, 87.94f * scaleY + offsetY
            )
            close()
        }

        drawPath(path = path, color = color, style = Fill)
    }
}