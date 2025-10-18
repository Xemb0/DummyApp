import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SalaryWithdrawCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .height(IntrinsicSize.Min)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFDE68A), // Yellow
                            Color(0xFFFAD4E4), // Pink
                            Color(0xFFBFE9F7), // Light Blue
                            Color(0xFFA5F3FC)  // Cyan
                        ),
                        start = Offset(0f, 300f),
                        end = Offset(1000f, 0f)
                    )
                )
        ) {
            // Radial blur effects on the right side
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .offset(x = 300.dp, y = (-50).dp)
                    .background(
                        color = Color(0x66A5F3FC), // Cyan with transparency
                        shape = CircleShape
                    )
                    .blur(80.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .offset(x = 200.dp, y = 40.dp)
                    .background(
                        color = Color(0x8086DCFB), // Lighter blue with transparency
                        shape = CircleShape
                    )
                    .blur(60.dp)
            )

            // Content
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Section
                Column {
                    Text(
                        text = "Available to withdraw",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        lineHeight = 40.sp
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(
                        text = "50% of salary earned",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                }

                Spacer( modifier = Modifier.height(16.dp) )
                // Bottom Section
                Column {
                    Text(
                        text = "Your balance",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "RM 12,689",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        lineHeight = 22.sp
                    )
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