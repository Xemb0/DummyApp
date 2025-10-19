package com.app.harigaji.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OptionTabs(
    height: Dp = 48.dp,
    modifier: Modifier = Modifier,
    bgColor: Color,
    ascentColor: Color,
    options: List<OptionTab>,
    selectedOptionTextColor: Color,
    selectedOption: OptionTab,
    onOptionSelected: (OptionTab) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(bgColor.copy(alpha = .3f)) // Fixed missing color reference
//            .dropShadow(
//                shape = CircleShape,
//                shadow = Shadow(
//                    offset = DpOffset(0.dp, 0.dp),
//                    radius = 3.dp,
//                    spread = 3.dp,
//                    color = bgColor
//                )
//            )
    ) {
        // Calculate the width for each option
        val indicatorWidth = maxWidth / options.size

        // Animate the indicator’s horizontal offset.
        val animatedOffset by animateDpAsState(
            targetValue = indicatorWidth * options.indexOf(selectedOption),
            animationSpec = tween(durationMillis = 300), label = ""
        )

        Card(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(indicatorWidth)
                .fillMaxHeight()
//                .padding(4.dp)
//                .dropShadow(
//                    shape = CircleShape,
//                    shadow = Shadow(
//                        offset = DpOffset(0.dp, 0.dp),
//                        radius = 3.dp,
//                        spread = 3.dp,
//                        color = bgColor
//
//                    )
//                )
                .background(ascentColor, CircleShape),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = ascentColor),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp,
                focusedElevation = 32.dp,
                hoveredElevation = 32.dp
            ),
            content ={}
        )

        // The clickable labels for each option.
        Row(
            modifier = Modifier.fillMaxSize().height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onOptionSelected(option) }
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.name,
                        color = if (selectedOption == option) selectedOptionTextColor else bgColor, // Fixed color reference
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class OptionTab(
    val id: String,
    val name: String,
    val icon: String,
    val color: String
)


@Preview
@Composable
fun OptionTabsPreview() {
    OptionTabs(
        bgColor = Color.White,
        ascentColor = Color.Gray,
        options = listOf(
            OptionTab("1", "Tab 1", "", ""),
            OptionTab("2", "Tab 2", "", ""),
//            OptionTab("3", "Tab 3", "", "")
        ),
        selectedOption = OptionTab("1", "Tab 1", "", ""),
        onOptionSelected = {},
        selectedOptionTextColor = Color.Yellow
    )
}