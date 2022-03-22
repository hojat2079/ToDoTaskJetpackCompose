package com.application.to_docompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.ui.theme.LARGE_PADDING
import com.application.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.application.to_docompose.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
        ) {
            if (priority != Priority.NONE)
                drawCircle(color = priority.color)
            else drawCircle(
                color = Color.Black,
                style = Stroke()
            )
        }
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            color = MaterialTheme.colors.onSurface,
            style = Typography.subtitle2
        )
    }
}

//@Composable
//@Preview
//fun PriorityItemPreview() {
//    PriorityItem(priority = Priority.NONE)
//}