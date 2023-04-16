package com.example.einsen.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tasks_Table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val task : String,
    val description : String,
    val urgentRating : Int,
    val importanceRating : Int,
    val date : String
)
