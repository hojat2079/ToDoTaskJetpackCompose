package com.application.to_docompose.ui.screen.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.application.to_docompose.R
import com.application.to_docompose.component.PriorityDropDownItem
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.ui.theme.LARGE_PADDING
import com.application.to_docompose.ui.theme.MEDIUM_PADDING
import com.application.to_docompose.ui.theme.SMALL_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescChanged: (String) -> Unit,
    priority: Priority,
    onPriorityChanged: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            singleLine = true,
            onValueChange = onTitleChanged,
            textStyle = MaterialTheme.typography.body1,
            label = {
                Text(text = stringResource(id = R.string.title))
            },
        )
        Spacer(
            modifier = Modifier.padding(top = MEDIUM_PADDING)
        )
        PriorityDropDownItem(
            priority = priority,
            onPriorityChanged = onPriorityChanged
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize()
                .padding(top = SMALL_PADDING),
            value = description,
            onValueChange = onDescChanged,
            textStyle = MaterialTheme.typography.body1,
            label = {
                Text(text = stringResource(id = R.string.desc))
            }
        )

    }
}

@Composable
@Preview
fun Preview() {
    TaskContent(
        title = "Hojat",
        onTitleChanged = {},
        description = " TEstTEstTEstTEstTEstTEstTEst",
        onDescChanged = {},
        priority = Priority.MEDIUM,
        onPriorityChanged = {}
    )
}