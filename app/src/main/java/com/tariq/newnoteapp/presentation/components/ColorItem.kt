package com.tariq.newnoteapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ColorItem(
    color: Color,
    onClick: () -> Unit
) {

    Surface(
        color = color,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(8.dp)
            .size(36.dp)
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.7f)),
        shadowElevation = 6.dp,
        tonalElevation = 6.dp
    ) {

    }

}