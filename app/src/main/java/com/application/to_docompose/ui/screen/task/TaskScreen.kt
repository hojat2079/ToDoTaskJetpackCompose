package com.application.to_docompose.ui.screen.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.application.to_docompose.R
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.data.model.ToDoTask
import com.application.to_docompose.util.Action
import com.application.to_docompose.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val desc: String by sharedViewModel.desc
    val title: String by sharedViewModel.title
    val priority: Priority by sharedViewModel.priority
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION)
                        navigateToListScreen(action)
                    else {
                        if (sharedViewModel.validateField())
                            navigateToListScreen(action)
                        else showToast(context)
                    }
                },
                selectedTask = selectedTask
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = { newTitle ->
                   sharedViewModel.updateTitle(newTitle)
                },
                description = desc,
                onDescChanged = { newDesc ->
                    sharedViewModel.desc.value = newDesc
                },
                priority = priority,
                onPriorityChanged = { newPriority ->
                    sharedViewModel.priority.value = newPriority
                }
            )
        }
    )
}

fun showToast(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.emptyToast),
        Toast.LENGTH_SHORT
    ).show()
}
