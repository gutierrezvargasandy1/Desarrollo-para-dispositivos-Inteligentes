package mx.edu.utng.memorymatch.presentation.board

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import mx.edu.utng.memorymatch.domain.model.Card

@Composable
fun CardItem(
    card: Card,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue   = if (card.isFlipped || card.isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label         = "cardFlip"
    )

    val isFrontVisible = rotation > 90f

    Box(
        modifier = modifier
            .size(52.dp)
            .graphicsLayer {
                rotationY      = rotation
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(8.dp))
            .background(
                when {
                    card.isMatched   -> Color(0xFF1B5E20)
                    isFrontVisible   -> Color(card.symbol.color)
                    else             -> Color(0xFF1A237E)
                }
            )
            .clickable(
                enabled = !card.isMatched,
                indication = null,
                interactionSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            ) {
                onTap()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isFrontVisible) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text     = card.symbol.emoji,
                    fontSize = 18.sp,
                    modifier = Modifier.graphicsLayer { rotationY = 180f }
                )
                Text(
                    text     = card.symbol.label,
                    fontSize = 7.sp,
                    color    = Color.White,
                    modifier = Modifier.graphicsLayer { rotationY = 180f }
                )
            }
        } else {
            Text(
                text       = "U",
                color      = Color(0xFFF9A825),
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier   = Modifier.graphicsLayer { rotationY = 180f }
            )
        }
    }
}