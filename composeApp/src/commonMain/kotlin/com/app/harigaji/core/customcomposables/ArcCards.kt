package com.app.harigaji.core.customcomposables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.logo_app_png
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConcaveBottomCard(
    corners: Dp = 8.dp,
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    arcHeight: Float = 50f,
    paddingBottom: Dp = arcHeight.dp / 3,
    content: @Composable BoxScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier.clip(RoundedCornerShape(bottomStart = corners, bottomEnd = corners)),
        shape = ConcaveArcBottomShape(arcHeight),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.wrapContentSize().padding(top = paddingBottom),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun ConcaveFromTopCard(
    shape: Shape= RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    arcHeight: Float = 50f,
    paddingTop: Dp = arcHeight.dp/3,
    content: @Composable BoxScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier.clip(shape),
        shape = ConvexTopArcShape(arcHeight),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.wrapContentSize().padding(top  = paddingTop ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun ConcaveTopCard(
    corners: Dp = 8.dp,
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    arcHeight: Float = 50f,
    paddingTop: Dp = arcHeight.dp / 3,
    content: @Composable BoxScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier.clip(RoundedCornerShape(bottomStart = corners, bottomEnd = corners)),
        shape = ConcaveArcTopShape(arcHeight),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier.wrapContentSize().padding(top = paddingTop),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun ConvexBottomCard(
    corners: Dp = 8.dp,
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    arcHeight: Float = 50f,
    background: Any? =null, // Supports Color, Brush, or Painter
    paddingBottom: Dp = arcHeight.dp / 3,
    content: @Composable BoxScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier
            .clip(RoundedCornerShape(topStart = corners, topEnd = corners))
            .padding(bottom = paddingBottom)
            .shadow(elevation, shape = ConvexBottomArcShape(arcHeight))
        ,
        shape = ConvexBottomArcShape(arcHeight),
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .let {
                    when (background) {
                        is Color -> it.background(background)
                        is Brush -> it.background(brush = background)
                        is Painter -> it.paint(background)
                        else -> it
                    }
                }
                .padding(bottom = paddingBottom),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Composable
fun ScreenCategoryProducts() {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConcaveBottomCard(
            arcHeight = 120f
        ) {
            Image(
                modifier = Modifier.size(80.dp).padding(8.dp),
                painter = painterResource(Res.drawable.logo_app_png),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Concave bottom", color = Color.White)
    }
}


@Preview
@Composable
fun ScreenCategoryProductsPreview() {
    ScreenCategoryProducts()
}



class ConcaveArcBottomShape(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Top-left to top-right and then to bottom-right.
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            // Draw the cubic Bézier curve from bottom-right to bottom-left.
            cubicTo(
                x1 = size.width * 0.75f,  // First control point: 75% along the width.
                y1 = size.height - arcHeight, // Raised by arcHeight.
                x2 = size.width * 0.25f,  // Second control point: 25% along the width.
                y2 = size.height - arcHeight, // Raised by arcHeight.
                x3 = 0f,                // End at bottom-left.
                y3 = size.height
            )
            close()
        }
        return Outline.Generic(path)
    }
}



class ConcaveArcTopShape(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Move to the bottom-left corner
            moveTo(0f, size.height)
            // Line to the bottom-right corner
            lineTo(size.width, size.height)
            // Line to the top-right corner
            lineTo(size.width, arcHeight)
            // Cubic Bézier curve from top-right to top-left
            cubicTo(
                x1 = size.width * 0.75f,  // First control point: 75% along the width
                y1 = 0f,                  // Lowered by arcHeight
                x2 = size.width * 0.25f,  // Second control point: 25% along the width
                y2 = 0f,                  // Lowered by arcHeight
                x3 = 0f,                  // End at top-left
                y3 = arcHeight
            )
            // Close the path
            close()
        }
        return Outline.Generic(path)
    }
}




class HalfCircleTopShape(private val radius: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radiusPx = with(density) { radius.toPx() }
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(0f, height) // Bottom-left corner
            lineTo(0f, 0f)    // Top-left corner
            lineTo(width / 2 - radiusPx, 0f) // Start of the arc
            quadraticTo(
                width / 2, radiusPx,         // Control point (peak of the dip)
                width / 2 + radiusPx, 0f     // End of the arc
            )
            lineTo(width, 0f)    // Top-right corner
            lineTo(width, height) // Bottom-right corner
            close()              // Back to bottom-left
        }
        return Outline.Generic(path)
    }
}

@Preview
@Composable
fun HalfCircleTopArcShapePreview() {

    HalfCircleTopConvexCard(
        radius = 20.dp,
        content = {
            Text("Half Circle Top Arc Shape")
        }
    )
}


@Composable
fun HalfCircleTopConvexCard(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    height: Dp = 100.dp,
    radius: Dp,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = modifier.size(width, height),
        shape = HalfCircleTopShape(radius),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
class ConvexTopArc(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start at the bottom-left corner.
            moveTo(0f, size.height)
            // Draw the left edge up to the top-left.
            lineTo(0f, 0f)
            // Draw a quadratic curve for the top edge:
            // from top-left (0,0) to top-right (size.width, 0)
            // with a control point that dips downward at (size.width/2, arcHeight).
            quadraticTo(
                x1 = size.width / 2f,
                y1 = arcHeight,
                x2 = size.width,
                y2 = 0f
            )
            // Draw the right edge down to the bottom-right.
            lineTo(size.width, size.height)
            // Close the shape by connecting back to the start.
            close()
        }
        return Outline.Generic(path)
    }
}


class ConvexArcTopShape(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start at the bottom-left corner.
            moveTo(0f, size.height)
            // Draw the left edge upward to the top-left corner.
            lineTo(0f, 0f)
            // Draw a quadratic curve from the top-left to the top-right corner.
            // The control point is placed above the top edge to create a convex arc.
            quadraticTo(
                x1 = size.width / 2f,
                y1 = -arcHeight, // Negative value positions the control point above the top edge.
                x2 = size.width,
                y2 = 0f
            )
            // Draw the right edge down to the bottom-right corner.
            lineTo(size.width, size.height)
            // Close the path by returning to the starting point.
            close()
        }
        return Outline.Generic(path)
    }
}


@Composable
fun SkewedCard(
    shape :Shape = RoundedCornerShape(8.dp),
    onClick:()-> Unit ={},
    modifier: Modifier = Modifier,
    topArcHeight: Float = 50f,       // for the top arc shape
    bottomArcHeight: Float = 50f,    // for the bottom arc shape
    paddingBottom: Dp =bottomArcHeight.dp/3,
    content: @Composable BoxScope.() -> Unit,
    color: Color = Color.White

) {
    Card (
        shape = ConvexTopArcShape(topArcHeight),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        modifier = modifier
            .clip(ConvexBottomArcShape(bottomArcHeight))
            .clip(shape)
            .clickable {
                onClick()
            }
            // Then apply rounded corners for smoother edges on the top corners
    ) {

        Box(
            modifier = Modifier.wrapContentSize()
                .padding(top = topArcHeight.dp/6, bottom = paddingBottom)
            ,
            contentAlignment = Alignment.Center
        ) {
            content()
        }

    }
}

class ConvexTopArcShape(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start at bottom-left corner
            moveTo(0f, size.height)
            // Draw line to bottom-right corner
            lineTo(size.width, size.height)
            // Draw line to top-right corner
            lineTo(size.width, 0f)
            // Draw cubic Bézier curve for the top edge
            cubicTo(
                x1 = size.width * 3 / 4, // First control point X
                y1 = arcHeight,          // First control point Y
                x2 = size.width / 4,     // Second control point X
                y2 = arcHeight,          // Second control point Y
                x3 = 0f,                 // End point X
                y3 = 0f                  // End point Y
            )
            // Close the path to form the shape
            close()
        }
        return Outline.Generic(path)
    }
}


class ConvexBottomArcShape(private val arcHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f) // Top-left.
            lineTo(size.width, 0f) // Top edge.
            lineTo(size.width, size.height - arcHeight) // Right edge to arc start.
            // Use a cubic Bézier for a smoother convex arc.
            cubicTo(
                x1 = size.width * 0.75f, y1 = size.height,
                x2 = size.width * 0.25f, y2 = size.height,
                x3 = 0f, y3 = size.height - arcHeight
            )
            close()
        }
        return Outline.Generic(path)
    }
}
