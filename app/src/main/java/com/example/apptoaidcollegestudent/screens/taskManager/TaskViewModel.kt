package com.example.apptoaidcollegestudent.screens.taskManager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptoaidcollegestudent.repository.TaskRepository
import com.example.einsen.database.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _task = MutableStateFlow<List<Task>>(emptyList())
    val task = _task.asStateFlow()

    private val _allTask = MutableStateFlow<List<Task>>(emptyList())
    val allTask = _allTask.asStateFlow()
    init {
        getAllTask()
    }

    fun getAllTask() = viewModelScope.launch {
        repository.getAllTasks().distinctUntilChanged().collect(){
            if (it.isEmpty()) {
                Log.d("TASK", "TASK IS NOte Found")
            } else {
                _allTask.value = it
                Log.d("TASK1", it.toString())
            }
        }

    }

    fun getTaskByDifficultyAndImportance(urgent: Int, importance: Int) = viewModelScope.launch {
        repository.getTaskByDifficultyAndImportance(urgent, importance).distinctUntilChanged()
            .collect() { listOfTask ->
                if (listOfTask.isEmpty()) {
                    Log.d("TASK", "TASK IS NOte Found")
                } else {
                    _task.value = listOfTask
                    Log.d("TASK1", listOfTask.toString())
                }
            }
    }


    fun insert(task: Task) = viewModelScope.launch { repository.insert(task) }
    fun delete(task: Task) = viewModelScope.launch { repository.delete(task) }
}