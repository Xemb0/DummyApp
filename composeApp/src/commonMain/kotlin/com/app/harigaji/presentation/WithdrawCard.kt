package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.theme.SpacerHorizontalSmall
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.rememberHorizontalPaddingSmall
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberCornerRadiusMedium
import com.app.harigaji.theme.rememberInnerHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberInnerHorizontalPaddingLarge
import com.app.harigaji.theme.rememberInnerHorizontalPaddingMedium
import com.app.harigaji.theme.rememberInnerVerticalPaddingLarge
import com.app.harigaji.theme.rememberInnerVerticalPaddingMedium
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeMedium
import com.app.harigaji.theme.rememberSizeSmall
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_calender
import harigaji.composeapp.generated.resources.ic_calender_filled
import harigaji.composeapp.generated.resources.ic_power
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WithdrawCard(amount: String, month: String, color: Color, modifier: Modifier = Modifier) {
    val innerPaddingLarge = rememberInnerHorizontalPaddingLarge()
    val paddingSmall = rememberHorizontalPaddingSmall()
    val cornerRadius = rememberCornerRadiusMedium()
    
    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 1.dp
        ),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 1f)),
        shape = cornerRadius
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = rememberInnerHorizontalPaddingExtraLarge())
                .padding(
                    vertical = rememberInnerVerticalPaddingLarge(),
                    horizontal = rememberInnerHorizontalPaddingLarge()
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(rememberSizeLarge())
                    .clip(CircleShape)
                    .background(color)
                    .innerShadow(
                shape = CircleShape,
                shadow = Shadow(
                    offset = DpOffset(0.dp, 0.dp),
                    radius = 4.dp,
                    spread = 2.dp,
                    color = Color(0x33000000)
                )
            )
                ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_power),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(rememberSizeSmall())
                )
            }


            Column {

                SpacerVerticalMedium()
                Text(
                    text = amount,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                SpacerVerticalSmall()
                Text(
                    text = "WITHDRAWAL",
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clip(CircleShape)
                        .innerShadow(
                            shape = CircleShape,
                            shadow = Shadow(
                                offset = DpOffset(0.dp, 0.dp),
                                radius = .5.dp,
                                spread = .1.dp,
                                color = Color(0x33000000)
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = .15f))
                        .padding(horizontal = rememberInnerHorizontalPaddingMedium()),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SpacerVerticalMedium()

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                ){
                    Icon(
                        painter = painterResource(Res.drawable.ic_calender_filled),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    SpacerHorizontalSmall()
                    Text(
                        text = month,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun WithdrawCardPreview() {
    WithdrawCard(amount = "$1,200", month = "October", color = Color(0xFF4CAF50), modifier = Modifier.padding(16.dp))
}


