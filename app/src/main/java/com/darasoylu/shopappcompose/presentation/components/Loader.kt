package com.darasoylu.shopappcompose.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.darasoylu.shopappcompose.presentation.theme.PrimaryGreen

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            color = PrimaryGreen
        )
    }
}

@Composable
fun LoadingAnimation() {
    // Create shimmer colors with green tones
    val shimmerColors = listOf(
        Color(0xFF8BC34A).copy(alpha = 0.6f),  // Light Green
        Color(0xFF4CAF50).copy(alpha = 0.2f),  // Medium Green
        Color(0xFF8BC34A).copy(alpha = 0.6f)   // Light Green again
    )

    // Create transition
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // Create the shimmer brush
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200, translateAnim - 200),
        end = Offset(translateAnim, translateAnim)
    )

    // Box with shimmer background
    ShimmerItem(
        brush = brush,
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
fun ShimmerItem(
    brush: Brush,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(shape)
            .background(brush)
    )
}