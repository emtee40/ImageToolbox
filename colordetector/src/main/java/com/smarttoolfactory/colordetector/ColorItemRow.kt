package com.smarttoolfactory.colordetector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ColorItemRow(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    populationPercent: String,
    colorData: ColorData,
    onClick: (ColorData) -> Unit,
) {
    Row(
        modifier = modifier
            .background(containerColor)
            .clickable {
                onClick(colorData)
            }
            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(colorData.color, shape = CircleShape)
                .border(
                    1.dp,
                    androidx.compose.material3.MaterialTheme.colorScheme.outline,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = colorData.hexText.uppercase(),
                fontSize = 16.sp,
                color = contentColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = populationPercent,
            fontSize = 16.sp,
            color = contentColor
        )
    }
}
