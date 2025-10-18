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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
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
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.bg_card
import org.jetbrains.compose.resources.painterResource
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
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(10.dp)
                    .paint(
                        painter = painterResource(Res.drawable.bg_card),
                        contentScale = ContentScale.Fit
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .rotate(-30f)
                    .offset(x = 250.dp, y = 10.dp)
                    .background(
                        color = Color.White.copy(alpha = .4f), // Lighter blue with transparency
                        shape = CircleShape
                    )
                    .blur(20.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(.9f)
                    .align(Alignment.TopEnd)
                    .rotate(-30f)
                    .offset(x = 230.dp, y = 8.dp)
                    .background(
                        color = Color(0x8086DCFB), // Lighter blue with transparency
                        shape = CircleShape
                    )
                    .blur(10.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(.4f)
                    .align(Alignment.TopEnd)
                    .rotate(-90f)
                    .offset(x = 80.dp, y = 30.dp)
                    .background(
                        color = Color.White.copy(alpha = .4f), // Lighter blue with transparency
                        shape = CircleShape
                    )
                    .blur(20.dp)
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
                        fontSize = 48.sp,
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