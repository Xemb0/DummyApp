package com.app.harigaji.presentation



import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun SlideToClockIn(
    modifier: Modifier = Modifier,
    onClockIn: () -> Unit = {}
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // Animation for reset
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDragging) offsetX else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    BoxWithConstraints(
        modifier = modifier
            .wrapContentWidth()
            .height(56.dp)
    ) {
        val sliderSize = 48.dp
        val sliderSizePx = with(androidx.compose.ui.platform.LocalDensity.current) { sliderSize.toPx() }

        // Measure text width
        val textWidth = with(androidx.compose.ui.platform.LocalDensity.current) {
            200.dp.toPx() // Approximate width for "Slide to clock In" text
        }
        val totalWidth = textWidth + 20f
        val maxOffset = totalWidth - sliderSizePx

        Box(
            modifier = Modifier
                .width(with(androidx.compose.ui.platform.LocalDensity.current) { totalWidth.toDp() })
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            // Progress background
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(
                        with(androidx.compose.ui.platform.LocalDensity.current) {
                            (animatedOffsetX + sliderSizePx).toDp()
                        }
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if(isDragging) 0.2f else 0f),
                        shape = RoundedCornerShape(28.dp)
                    )
            )

            // Text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Slide to clock In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier.alpha(1f - (animatedOffsetX / maxOffset).coerceIn(0f, 1f))
                )
            }

            // Slider button
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = if (isDragging) offsetX.roundToInt() else animatedOffsetX.roundToInt(),
                            y = 0
                        )
                    }
                    .padding(4.dp)
                    .size(sliderSize)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = {
                                isDragging = true
                            },
                            onDragEnd = {
                                isDragging = false
                                if (offsetX >= maxOffset * 0.8f) {
                                    // Completed
                                    isCompleted = true
                                    onClockIn()
                                    offsetX = maxOffset
                                } else {
                                    // Reset
                                    offsetX = 0f
                                }
                            },
                            onDragCancel = {
                                isDragging = false
                                offsetX = 0f
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                offsetX = (offsetX + dragAmount).coerceIn(0f, maxOffset)
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Slide",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }
}

// Preview
@Composable
fun SlideToClockInPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        SlideToClockIn(
            onClockIn = {
                println("Clocked in!")
            }
        )
    }
}
