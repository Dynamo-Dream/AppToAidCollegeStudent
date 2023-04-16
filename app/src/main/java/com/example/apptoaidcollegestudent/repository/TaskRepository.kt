package com.example.apptoaidcollegestudent.repository

import com.example.einsen.database.Task
import com.example.einsen.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDatabaseDao: TaskDatabaseDao) {
    suspend fun insert(task: Task) = taskDatabaseDao.insert(task)
    suspend fun delete(task: Task) = taskDatabaseDao.delete(task)
    fun getTaskByDifficultyAndImportance(urgency : Int, importance : Int) : Flow<List<Task>> = taskDatabaseDao.getTaskByDifficultyAndImportance(urgency,importance).flowOn(
        Dispatchers.IO).conflate()
    fun getAllTasks() : Flow<List<Task>> = taskDatabaseDao.getAllTask()
}