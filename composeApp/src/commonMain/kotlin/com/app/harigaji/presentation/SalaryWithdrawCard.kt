import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberHorizontalPaddingExtraLarge
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
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
fun SalaryWithdrawCard(modifier: Modifier = Modifier, onBalanceCardClick: () -> Unit = {}) {
    val cornerRadius = rememberCornerRadiusLarge()
    val paddingLarge = rememberHorizontalPaddingLarge()
    val paddingExtraLarge = rememberHorizontalPaddingExtraLarge()
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = cornerRadius,
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

            // Decorative circles - positioned absolutely, won't affect parent size
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .rotate(-90f)
                    .offset(x = 120.dp, y = (10).dp)
                    .background(
                        color = Color.White.copy(alpha = .4f),
                        shape = CircleShape
                    )
                    .blur(20.dp)
            )

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopEnd)
                    .rotate(-30f)
                    .offset(x = 200.dp, y = (-70).dp)
                    .background(
                        color = Color(0x8086DCFB),
                        shape = CircleShape
                    )
                    .blur(10.dp)
            )

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .rotate(-90f)
                    .offset(x = 50.dp, y = (-30).dp)
                    .background(
                        color = Color.White.copy(alpha = .4f),
                        shape = CircleShape
                    )
                    .blur(20.dp)
            )

            // Content - This determines the card size
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = paddingExtraLarge, vertical = paddingLarge),
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

                Spacer(modifier = Modifier.height(16.dp))

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