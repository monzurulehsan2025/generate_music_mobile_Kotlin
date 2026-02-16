package com.aura.musicapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.aura.musicapp.ui.theme.AuraMagenta
import com.aura.musicapp.ui.theme.AuraPurple
import com.aura.musicapp.ui.theme.AuraCyan

@Composable
fun MusicVisualizer(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")
    
    val barCount = 12
    val durations = listOf(800, 1100, 900, 1300, 1000, 1200, 850, 1050, 950, 1150, 1250, 900)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        for (i in 0 until barCount) {
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durations[i % durations.size], easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "barScale-$i"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp * scale)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                if (i % 3 == 0) AuraPurple else if (i % 3 == 1) AuraMagenta else AuraCyan,
                                if (i % 3 == 0) AuraPurple.copy(alpha = 0.5f) else AuraCyan.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }
    }
}
