package com.application.to_docompose.ui.screen.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.application.to_docompose.R
import com.application.to_docompose.ToDoApp
import com.application.to_docompose.component.DisplayAlertDialog
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.data.model.ToDoTask
import com.application.to_docompose.ui.theme.topAppBarBackgroundColor
import com.application.to_docompose.ui.theme.topAppBarContentColor
import com.application.to_docompose.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedTask == null)
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    else ExistingTaskAppBar(
        selectedTask = selectedTask,
        navigateToListScreen = navigateToListScreen
    )
}

@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            BackIconAction(
                onBackClicked = navigateToListScreen
            )
        },
        title = {
            Text(
                text = stringResource(R.string.add_task),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            AddIconAction(onAddClicked = navigateToListScreen)
        }
    )
}

@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            CloseIconAction(
                onCloseClicked = navigateToListScreen
            )
        },
        title = {
            Text(
                text = selectedTask.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ExistingTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        }
    )
}

@Composable
fun ExistingTaskAppBarActions(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    var openDialog: Boolean by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(
            id = R.string.deleteTask,
            selectedTask.title
        ),
        text = stringResource(
            id = R.string.deleteTaskDescription,
            selectedTask.title
        ),
        confirmClick = { navigateToListScreen(Action.DELETE) },
        closeClick = { openDialog = false },
        openDialog = openDialog
    )
    DeleteIconAction(
        onDeleteClicked = {
            openDialog = true
        },
    )
    UpdateIconAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun AddIconAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onAddClicked(Action.ADD)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(
                R.string.add_task
            ), tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun BackIconAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onBackClicked(Action.NO_ACTION)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(
                R.string.back_action
            ), tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun CloseIconAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onCloseClicked(Action.NO_ACTION)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(
                R.string.close_action
            ), tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateIconAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onUpdateClicked(Action.UPDATE)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(
                R.string.close_action
            ), tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteIconAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onDeleteClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(
                R.string.delete_action
            ), tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview(
) {
    NewTaskAppBar(
        navigateToListScreen = { }
    )
}

@Composable
@Preview
fun ExistingTaskAppBarPreview(
) {
    ExistingTaskAppBar(selectedTask =
    ToDoTask(
        id = 0,
        title = "Hojat",
        desc = "descdescdescdescdescdescdesc",
        priority = Priority.HIGH
    ),
        navigateToListScreen = { }
    )
}


