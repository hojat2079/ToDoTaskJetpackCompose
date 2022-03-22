package com.application.to_docompose.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.data.model.ToDoTask
import com.application.to_docompose.data.repo.PriorityRepository
import com.application.to_docompose.data.repo.ToDoRepository
import com.application.to_docompose.util.Action
import com.application.to_docompose.util.MAX_LENGTH_TITLE
import com.application.to_docompose.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val todoTaskRepository: ToDoRepository,
    private val priorityRepository: PriorityRepository
) : ViewModel() {


    private val _allTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTask = _allTask.asStateFlow()
    private val _searchTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchTask = _searchTask.asStateFlow()
    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState = _sortState.asStateFlow()

    var desc: MutableState<String> = mutableStateOf("")
    var title: MutableState<String> = mutableStateOf("")
    var priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    var id: MutableState<Int> = mutableStateOf(0)
    var action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)


    fun getAllTask() {
        _allTask.value = RequestState.Loading
        try {
            viewModelScope.launch {
                todoTaskRepository.getAllTask.collect {
                    _allTask.value = RequestState.Success(it)
                }
            }
        } catch (ex: Exception) {
            _allTask.value = RequestState.Error(ex)
        }
    }

    fun getSearchTask(searchQuery: String) {
        _searchTask.value = RequestState.Loading
        try {
            viewModelScope.launch {
                todoTaskRepository.searchDatabase("%$searchQuery%").collect {
                    _searchTask.value = RequestState.Success(it)
                }
            }
        } catch (ex: Exception) {
            _searchTask.value = RequestState.Error(ex)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            todoTaskRepository.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    fun handleDatabaseAction(action: Action) {
        Log.d("action -> $action ", "action -> $action ")
        when (action) {
            Action.ADD -> addTask()
            Action.DELETE -> deleteTask()
            Action.UPDATE -> updateTask()
            Action.DELETE_ALL -> deleteAll()
            Action.UNDO -> addTask(id.value)
            else -> {}
        }
        if (this.action.value != Action.NO_ACTION)
            this.action.value = Action.NO_ACTION
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            todoTaskRepository.updateTask(
                ToDoTask(
                    id = id.value,
                    title = title.value,
                    desc = desc.value,
                    priority = priority.value,
                )
            )
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            todoTaskRepository.deleteTask(
                ToDoTask(
                    id = id.value,
                    title = title.value,
                    desc = desc.value,
                    priority = priority.value,
                )
            )
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            todoTaskRepository.deleteAllTask()
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            todoTaskRepository.addTask(
                ToDoTask(
                    title = title.value,
                    desc = desc.value,
                    priority = priority.value
                )
            )
        }
    }

    private fun addTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoTaskRepository.addTask(
                ToDoTask(
                    id = id,
                    title = title.value,
                    desc = desc.value,
                    priority = priority.value
                )
            )
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_LENGTH_TITLE)
            title.value = newTitle
    }

    fun validateField(): Boolean {
        return desc.value.isNotEmpty() && title.value.isNotEmpty()
    }

    fun updateContentTask(toDoTask: ToDoTask?) {
        if (toDoTask != null) {
            desc.value = toDoTask.desc
            title.value = toDoTask.title
            id.value = toDoTask.id
            priority.value = toDoTask.priority
        } else {
            desc.value = ""
            id.value = 0
            title.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRepository.editSortState(priority = priority)
        }
    }

    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                priorityRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (ex: Exception) {
            _sortState.value = RequestState.Error(ex)
        }
    }
}