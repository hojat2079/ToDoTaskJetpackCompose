package com.application.to_docompose.data.model

import androidx.compose.ui.graphics.Color
import com.application.to_docompose.ui.theme.HighPriorityColor
import com.application.to_docompose.ui.theme.LowPriorityColor
import com.application.to_docompose.ui.theme.MediumPriorityColor
import com.application.to_docompose.ui.theme.NonePriorityColor

enum class Priority(var color: Color) {
    LOW(LowPriorityColor),
    MEDIUM(MediumPriorityColor),
    HIGH(HighPriorityColor),
    NONE(NonePriorityColor)
}