package com.example.assignment.core.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Configure component to receive clicks via input or accessibility "click" event.
 *
 * Add this modifier to the element to make it clickable within its bounds and show no indication.
 *
 * @param enabled Controls the enabled state. When `false`, [onClick], and this modifier will
 * @param onClick will be called when user clicks on the element.
 */
fun Modifier.clickable(enabled: Boolean = true, onClick: () -> Unit) = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = enabled,
        onClick = onClick
    )
}