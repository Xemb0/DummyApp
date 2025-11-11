package com.app.harigaji.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.chat.ChatMessage
import com.app.harigaji.theme.SpacerHorizontalMedium
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberHorizontalPaddingMedium
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberTextStyleHeadline
import com.app.harigaji.theme.rememberTextStyleSmall
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun MessageItem(sender: String, message: List<ChatMessage>, time: String, onClick: () -> Unit) {
    val paddingLarge = rememberHorizontalPaddingLarge()
    val paddingMedium = rememberHorizontalPaddingMedium()
    val cornerRadius = rememberCornerRadiusLarge()
    
    Column {

        
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(horizontal = paddingLarge, vertical = paddingMedium).debugUi(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = cornerRadius
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
               Image(
                    painter = painterResource(Res.drawable.ic_profile),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .innerShadow(
                            shape = CircleShape,
                            shadow = Shadow(
                                offset = DpOffset(0.dp, 0.dp),
                                radius = 1.dp,
                                spread = .5.dp,
                                color = Color(0x33000000)
                            )
                        )
                        .clip(CircleShape)
               )

                SpacerHorizontalMedium()
                Column(modifier = Modifier.weight(1f).debugUi()) {
                    BasicText(
                        text = sender,
                        style = rememberTextStyleHeadline().copy(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
                        ),
                    )
                    BasicText(
                        text = message.lastOrNull()?.message?:"",
                        style = rememberTextStyleSmall().copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                        ),
                    )
                }

                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}