package com.application.to_docompose.ui.screen.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.application.to_docompose.R
import com.application.to_docompose.util.Action
import com.application.to_docompose.util.SearchAppBarState
import com.application.to_docompose.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTask()
        sharedViewModel.readSortState()
    }
    val action by sharedViewModel.action

    val allTask by sharedViewModel.allTask.collectAsState()
    val searchTask by sharedViewModel.searchTask.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()


    var searchAppBarState: SearchAppBarState by rememberSaveable {
        mutableStateOf(SearchAppBarState.CLOSED)
    }

    var searchTextState: String by rememberSaveable {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()
    ShowSnackBar(
        scaffoldState = scaffoldState,
        action = action,
        handleDatabaseAction = {
            sharedViewModel.handleDatabaseAction(action = action)
        },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        title = setMessageSnackBar(
            title = sharedViewModel.title.value,
            action = action
        ),
        actionLabel = setLabelSnackBar(action = action)
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListTopBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                searchAbbStateChangedClicked = {
                    searchAppBarState = it
                },
                searchTextChanged = {
                    searchTextState = it
                }
            )
        },
        content = {
            ListContent(
                onItemClicked = navigateToTaskScreen,
                tasks = allTask,
                sortState = sortState,
                searchTasks = searchTask,
                searchAppBarState = searchAppBarState,
                onSwipeToDoTask = { action, toDoTask ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateContentTask(toDoTask = toDoTask)
                }
            )
        },
        floatingActionButton = {
            ListFab(
                onFabClicked = {
                    searchAppBarState = SearchAppBarState.CLOSED
                    navigateToTaskScreen(it)
                },
            )
        }
    )
}

@Composable
fun ShowSnackBar(
    scaffoldState: ScaffoldState,
    action: Action,
    handleDatabaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    title: String,
    actionLabel: String
) {
    val scope = rememberCoroutineScope()
    handleDatabaseAction()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = title,
                    duration = SnackbarDuration.Short,
                    actionLabel = actionLabel
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

@Composable
private fun setLabelSnackBar(action: Action) =
    if (action == Action.DELETE) stringResource(R.string.undo) else stringResource(id = R.string.ok)

@Composable
private fun setMessageSnackBar(action: Action, title: String) =
    if (action == Action.DELETE_ALL) stringResource(R.string.remove_all) else "${action.name} : $title"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
        action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}


