package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import com.app.harigaji.theme.SpacerHorizontalLarge
import com.app.harigaji.theme.SpacerHorizontalMedium
import com.app.harigaji.theme.SpacerHorizontalSmall
import com.app.harigaji.theme.rememberHorizontalPaddingSmall
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeMedium
import com.app.harigaji.theme.rememberSizeSmall
import com.app.harigaji.theme.rememberTextStyleHeadline
import com.app.harigaji.theme.rememberTextStyleSmall

@Composable
fun TransactionItem(
    label: String,
    value: String,
    subValue: String,
    amount: String,
    status: String,
    statusColor: Color,
    modifier: Modifier,
) {
    val paddingSmall = rememberHorizontalPaddingSmall()
    val paddingMedium = rememberHorizontalPaddingSmall()
    val paddingLarge = rememberHorizontalPaddingLarge()
    val cornerRadius = rememberCornerRadiusLarge()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = cornerRadius
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingLarge, vertical = paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(rememberSizeExtraLarge())
                    .clip(CircleShape)
                    .background(statusColor.copy(alpha = 0.2f))
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

                Box(
                    modifier = Modifier
                        .size(rememberSizeMedium())
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .background(statusColor)
                ){
                Icon(
                    imageVector = when(status) {
                        "Successful" -> Icons.Default.Check
                        "Failed" -> Icons.Default.Close
                        else -> Icons.Default.Sync
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(rememberSizeSmall()).align(Alignment.Center)
                )
                }
            }

            SpacerHorizontalMedium()
            Column(modifier = Modifier.weight(1f)) {
                BasicText(
                    text = label,
                    style = rememberTextStyleHeadline().copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
                    ),
                )
                BasicText(
                    text = value,
                    style = rememberTextStyleSmall().copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
                    ),
                )
//                val (date, time) = date.split(" ")

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = subValue,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "RM $amount",
                    style = MaterialTheme.typography.labelLarge,
                    color = statusColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = when(status) {
                            "Successful" -> Icons.Default.ArrowCircleUp
                            "Failed" -> Icons.Default.ArrowCircleDown
                            else -> Icons.Default.Schedule

                        },
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(14.dp)
                    )
                    SpacerHorizontalSmall()
                    Text(
                        text = status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
                SpacerHorizontalLarge()
        }
    }
}
