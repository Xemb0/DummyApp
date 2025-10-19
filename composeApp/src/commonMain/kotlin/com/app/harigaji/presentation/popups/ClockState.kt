package com.app.harigaji.presentation.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_cross
import harigaji.composeapp.generated.resources.ic_done
import harigaji.composeapp.generated.resources.ic_farward
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Data class to hold clock state
data class ClockState(
    val isClockIn: Boolean,
    val time: String = "9:00 pm"
)

@Composable
fun AttendanceClockScreen(
    paddingValues: PaddingValues,
    clockState: ClockState,
    onGoToHome: () -> Unit = {}
) {
    val backgroundColor = if (clockState.isClockIn) Color(0xFF32ba7c) else Color(0xFFEF5350)
    val icon = if (clockState.isClockIn) Res.drawable.ic_done else Res.drawable.ic_cross
    val title = if (clockState.isClockIn) "Clocked in Successful" else "Clocked in Out"
    val message = if (clockState.isClockIn) "clocked in" else "clocked out"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 32.dp).weight(1f)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(32.dp))

                    // Title
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    Text(
                        text = "You have successfully $message",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "your attendance at",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Time
                    Text(
                        text = clockState.time,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                }
                // Go to Home Button
                Button(
                    onClick = onGoToHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF32ba7c)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Go to Home",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(Res.drawable.ic_farward),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                    Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

// Preview for Clock In
@Preview
@Composable
fun PreviewClockInScreen() {
    MaterialTheme {
        AttendanceClockScreen(
            paddingValues = PaddingValues(0.dp),

            clockState = ClockState(isClockIn = true, time = "9:00 pm")
        )
    }
}

// Preview for Clock Out
@Preview
@Composable
fun PreviewClockOutScreen() {
    MaterialTheme {
        AttendanceClockScreen(
            paddingValues = PaddingValues(0.dp),

            clockState = ClockState(isClockIn = false, time = "9:00 pm")
        )
    }
}

// Example usage in an Activity or another Composable
@Composable
fun AttendanceScreenExample() {
    var isClockIn by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        AttendanceClockScreen(
            paddingValues = PaddingValues(0.dp),
            clockState = ClockState(isClockIn = isClockIn, time = "9:00 pm"),
            onGoToHome = {
                // Handle navigation to home
                // navController.navigate("home")
            }
        )

        // Toggle button for preview (remove in production)
        Button(
            onClick = { isClockIn = !isClockIn },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Toggle State")
        }
    }
}