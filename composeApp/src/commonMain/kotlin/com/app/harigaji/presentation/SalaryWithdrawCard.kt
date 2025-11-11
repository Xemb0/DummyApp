import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberHorizontalPaddingExtraLarge
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusMedium
import com.app.harigaji.theme.rememberInnerHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberInnerHorizontalPaddingLarge
import com.app.harigaji.theme.rememberInnerHorizontalPaddingMedium
import com.app.harigaji.theme.rememberInnerVerticalPaddingMedium
import com.app.harigaji.theme.rememberTextSizeExtraLarge
import com.app.harigaji.theme.rememberTextSizeHeadline
import com.app.harigaji.theme.rememberTextSizeLarge
import com.app.harigaji.theme.rememberTextSizeMedium
import com.app.harigaji.theme.rememberTextSizeTitle
import com.app.harigaji.theme.rememberTextStyleTitle
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.bg_card
import harigaji.composeapp.generated.resources.logo_app
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SalaryWithdrawCard(modifier: Modifier = Modifier, onBalanceCardClick: () -> Unit = {}) {
    val paddingLarge = rememberInnerVerticalPaddingMedium()
    val paddingExtraLarge = rememberInnerHorizontalPaddingExtraLarge()

    // State to trigger animations
    var animationStarted by remember { mutableStateOf(false) }

    // Trigger animation on launch
    LaunchedEffect(Unit) {
        animationStarted = true
    }

    // Bounce animation for circles - slower bounce
    val circle1Scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val circle2Scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow,
            visibilityThreshold = 0.01f
        )
    )

    val circle3Scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    // Logo bounce animation - slower
    val logoScale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(durationMillis = 2500, easing = FastOutSlowInEasing)
    )

    // Infinite subtle rotation animations - starts after initial bounce
    val infiniteTransition = rememberInfiniteTransition()

    val circle1Rotation by infiniteTransition.animateFloat(
        initialValue = -90f,
        targetValue = -80f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle3Rotation by infiniteTransition.animateFloat(
        initialValue = -90f,
        targetValue = -100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = rememberCornerRadiusMedium(),
        onClick = onBalanceCardClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clipToBounds() // Clip children to bounds
        ) {
            // Background layer - fills the size determined by content
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(20.dp)
                    .paint(
                        painter = painterResource(Res.drawable.bg_card),
                        contentScale = ContentScale.Crop
                    )
            )

            // Shadow overlay on the right side for logo visibility
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(100.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.15f)
                            )
                        )
                    )
            )

            // Decorative circles - animated with bounce effect then subtle infinite movement
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .rotate(circle1Rotation)
                    .offset(x = 120.dp, y = 10.dp)
                    .scale(circle1Scale)
                    .background(
                        color = Color.White.copy(alpha = .4f),
                        shape = CircleShape
                    )
                    .innerShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            offset = DpOffset(0.dp, 0.dp),
                            radius = 6.dp,
                            spread = 3.dp,
                            color = Color(0x33000000)
                        )
                    )
                    .blur(20.dp)
            )



            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .rotate(circle3Rotation)
                    .offset(x = 50.dp, y = -30.dp)
                    .scale(circle3Scale)
                    .background(
                        color = Color.White.copy(alpha = .4f),
                        shape = CircleShape
                    )
                    .innerShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            offset = DpOffset(0.dp, 0.dp),
                            radius = 4.dp,
                            spread = 2.dp,
                            color = Color(0x33000000)
                        )
                    )
                    .blur(20.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingExtraLarge, vertical = paddingLarge),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth().debugUi(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    SpacerVerticalSmall()


                    Text(
                        text = "Available to withdraw",
                        fontSize = rememberTextSizeExtraLarge(),
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1F2937),
                        lineHeight = 18.sp
                    )

                    Text(
                        text = "50% of salary earned",
                        fontSize = rememberTextSizeMedium(),
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 16.sp,
                        color = Color(0xFF374151)
                    )

                }

                // Top Section
                Column (
                    modifier = Modifier.fillMaxWidth().debugUi(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){

                    Text(
                        text = "Your balance",
                        fontSize = rememberTextSizeMedium(),
                        fontWeight = FontWeight.Bold,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RM 12,689",
                            fontSize = rememberTextSizeExtraLarge(),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827),
                            lineHeight = 20.sp
                        )

                        Icon(
                            painter = painterResource(Res.drawable.logo_app),
                            modifier = Modifier
                                .width(68.dp)
                                .wrapContentHeight()
                                .scale(logoScale)
                                .alpha(logoAlpha),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalaryWithdrawCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(16.dp)
    ) {
        SalaryWithdrawCard()
    }
}