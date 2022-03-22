package com.application.to_docompose.ui.screen.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.to_docompose.R
import com.application.to_docompose.component.DisplayAlertDialog
import com.application.to_docompose.component.PriorityItem
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.ui.theme.LARGE_PADDING
import com.application.to_docompose.ui.theme.Typography
import com.application.to_docompose.ui.theme.topAppBarBackgroundColor
import com.application.to_docompose.ui.theme.topAppBarContentColor
import com.application.to_docompose.util.Action
import com.application.to_docompose.util.TrailingIconState
import com.application.to_docompose.util.SearchAppBarState
import com.application.to_docompose.viewmodel.SharedViewModel

@Composable
fun ListTopBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    searchAbbStateChangedClicked:
        (SearchAppBarState) -> Unit,
    searchTextChanged: (String) -> Unit
) {

    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppbar(
                onSearchClicked = {
                    searchAbbStateChangedClicked(SearchAppBarState.OPENED)
                },
                onSortClicked = {
                    sharedViewModel.updateSortState(it)
                },
                onDeleteClicked = {
                    sharedViewModel.action.value = Action.DELETE_ALL
                }
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onSearchClicked = { searchQuery ->
                    sharedViewModel.getSearchTask(searchQuery)
                    searchAbbStateChangedClicked(SearchAppBarState.TRIGGERED)
                },
                onCloseClicked = {
                    searchAbbStateChangedClicked(SearchAppBarState.CLOSED)
                    searchTextChanged("")
                },
                onTextChanged = { newText ->
                    searchTextChanged(newText)
                })
        }
    }

}

@Composable
fun DefaultListAppbar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        title = {
            Text(
                text = stringResource(
                    R.string.list_screen_title_appbar
                ),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        }, actions = {
            ListTopBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    )
}

@Composable
fun ListTopBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {

    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.deleteAllTask),
        text = stringResource(id = R.string.deleteAllTaskDescription),
        confirmClick = { onDeleteClicked() },
        closeClick = { openDialog = false },
        openDialog = openDialog
    )
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = { openDialog = true })
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onSearchClicked()
        }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(
                R.string.search_action
            ),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(
                R.string.priority_action
            ),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }

}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = stringResource(
                R.string.delete_all_action
            ),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClicked()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2,
                    modifier = Modifier.padding(start = LARGE_PADDING)
                )
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState: TrailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_CLOSE)
    }
    Surface(
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource
                        (R.string.search),
                    color = Color.White,
                )
            },
            textStyle = TextStyle(
                color =
                MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource
                            (id = R.string.search_action),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            }, trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChanged("")
                            } else onCloseClicked()
                        }
                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChanged("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource
                            (R.string.close_action),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                disabledIndicatorColor = Color.Green,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
fun DefaultListAppBarPreview() {
    DefaultListAppbar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onSearchClicked = {},
        onCloseClicked = {},
        onTextChanged = {}
    )
}