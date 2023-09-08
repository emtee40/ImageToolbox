@file:Suppress("AnimateAsStateLabel")

package ru.tech.imageresizershrinker.presentation.root.utils.modifier

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.imageresizershrinker.presentation.main_screen.components.boxShadow
import ru.tech.imageresizershrinker.presentation.root.theme.outlineVariant
import ru.tech.imageresizershrinker.presentation.root.widget.utils.LocalSettingsState

fun Modifier.container(
    shape: Shape = RoundedCornerShape(16.dp),
    color: Color = Color.Unspecified,
    resultPadding: Dp = 4.dp,
    borderColor: Color? = null
) = composed {
    val settingsState = LocalSettingsState.current
    val colorScheme = MaterialTheme.colorScheme
    val color1 = if (color.isUnspecified) {
        colorScheme.surfaceColorAtElevation(1.dp)
    } else {
        color.compositeOver(colorScheme.background)
    }

    val density = LocalDensity.current

    val genericModifier = Modifier.drawWithCache {
        val outline = shape.createOutline(
            size,
            layoutDirection,
            density
        )
        onDrawWithContent {
            drawOutline(
                outline = outline,
                color = color1
            )
            if (settingsState.borderWidth > 0.dp) {
                drawOutline(
                    outline = outline,
                    color = borderColor ?: colorScheme.outlineVariant(0.1f, color1),
                    style = Stroke(with(density) { settingsState.borderWidth.toPx() })
                )
            }
            drawContent()
        }
    }

    val cornerModifier = Modifier
        .background(
            color = color1,
            shape = shape
        )
        .border(
            width = LocalSettingsState.current.borderWidth,
            color = borderColor ?: MaterialTheme.colorScheme.outlineVariant(0.1f, color1),
            shape = shape
        )

    this
        .shadow(
            shape = shape,
            elevation = animateDpAsState(if(settingsState.borderWidth > 0.dp) 0.dp else 1.dp).value,
            clip = false
        )
        .then(
            if (shape is CornerBasedShape) cornerModifier
            else genericModifier
        )
        .clip(shape)
        .then(if (resultPadding > 0.dp) Modifier.padding(resultPadding) else Modifier)
}