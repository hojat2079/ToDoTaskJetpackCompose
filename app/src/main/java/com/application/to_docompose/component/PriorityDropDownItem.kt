package com.application.to_docompose.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.to_docompose.R
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.application.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityDropDownItem(
    priority: Priority,
    onPriorityChanged: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue =
        if (expanded) 180f else 0f
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha =
                    ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            )
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            if (priority != Priority.NONE)
                drawCircle(color = priority.color)
            else drawCircle(
                color = Color.Black,
                style = Stroke()
            )
        }
        Text(
            modifier = Modifier.weight(8f),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            modifier = Modifier
                .alpha(
                    ContentAlpha.medium
                )
                .rotate(angle)
                .weight(1.5f),
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(
                    R.string.arrow_drop_down
                ),
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPriorityChanged(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPriorityChanged(Priority.MEDIUM)
                }
            ) {
                PriorityItem(priority = Priority.MEDIUM)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPriorityChanged(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }
        }
    }

}

@Composable
@Preview
fun PriorityDropDownItemPreview() {
    PriorityDropDownItem(
        priority = Priority.HIGH,
        onPriorityChanged = {}
    )
}