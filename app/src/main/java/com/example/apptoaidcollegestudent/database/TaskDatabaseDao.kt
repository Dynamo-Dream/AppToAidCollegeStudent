package com.example.einsen.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDatabaseDao {

    @Query("SELECT * from Tasks_Table")
    fun getAllTask() : Flow<List<Task>>

    @Query("SELECT * from Tasks_Table where urgentRating =:urgency AND importanceRating =:importance")
    fun getTaskByDifficultyAndImportance(urgency : Int, importance : Int) : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}