package com.application.to_docompose.ui.screen.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.application.to_docompose.R
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.data.model.ToDoTask
import com.application.to_docompose.ui.theme.*
import com.application.to_docompose.util.Action
import com.application.to_docompose.util.RequestState
import com.application.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    onItemClicked: (Int) -> Unit,
    tasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    sortState: RequestState<Priority>,
    onSwipeToDoTask: (Action, ToDoTask) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchTasks is RequestState.Success) {
                    HandleListContent(
                        onItemClicked = onItemClicked,
                        list = searchTasks.data,
                        onSwipeToDoTask = onSwipeToDoTask
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(
                        onItemClicked = onItemClicked,
                        list = tasks.data,
                        onSwipeToDoTask = onSwipeToDoTask
                    )
                }
            }
            sortState.data == Priority.HIGH -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(
                        onItemClicked = onItemClicked,
                        list = tasks.data.sortedBy { it.priority }.reversed(),
                        onSwipeToDoTask = onSwipeToDoTask
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(
                        onItemClicked = onItemClicked,
                        list = tasks.data.sortedBy { it.priority },
                        onSwipeToDoTask = onSwipeToDoTask
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    onItemClicked: (Int) -> Unit,
    onSwipeToDoTask: (Action, ToDoTask) -> Unit,
    list: List<ToDoTask>,
) {
    if (list.isEmpty()) {
        EmptyContent()
    } else DisplayTask(
        onItemClicked = onItemClicked,
        tasks = list,
        onSwipeToDoTask = onSwipeToDoTask
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTask(
    onItemClicked: (Int) -> Unit,
    onSwipeToDoTask: (Action, ToDoTask) -> Unit,
    tasks: List<ToDoTask>
) {
    LazyColumn {
        items(items = tasks,
            key = { task ->
                task.id
            }) { task ->
            val swipeDismissState = rememberDismissState()
            val dismissDirection = swipeDismissState.dismissDirection
            val isDismissed = swipeDismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDoTask(Action.DELETE, task)
                }
            }

            val degrees by animateFloatAsState(
                targetValue = if (swipeDismissState.targetValue == DismissValue.Default)
                    0f
                else -45f
            )

            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = swipeDismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = { RedBackground(degrees) },
                    dismissThresholds = { FractionalThreshold(0.2f) }
                ) {
                    TaskItem(
                        onItemClicked = onItemClicked,
                        toDoTask = task
                    )
                }
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .background(HighPriorityColor)
            .padding(LARGEST_PADDING)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.deleteTaskDescription),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    onItemClicked: (Int) -> Unit,
    toDoTask: ToDoTask
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        elevation = TASK_ITEM_ELEVATION,
        shape = RectangleShape,
        onClick = {
            onItemClicked(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = toDoTask.title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.taskItemTextColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                ) {
                    Canvas(
                        modifier = Modifier.size(
                            PRIORITY_INDICATOR_SIZE
                        )
                    ) {
                        if (toDoTask.priority != Priority.NONE)
                            drawCircle(color = toDoTask.priority.color)
                        else drawCircle(
                            color = Color.Black,
                            style = Stroke()
                        )
                    }
                }
            }
            Text(
                text = toDoTask.desc,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.taskItemTextColor,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}