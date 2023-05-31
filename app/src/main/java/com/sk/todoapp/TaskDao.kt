package com.sk.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): LiveData<MutableList<Tasks>>

    @Insert
    suspend fun insertTask(task: Tasks)

    @Update
    suspend fun updateTask(task: Tasks)

    @Delete
    suspend fun deleteTask(task: Tasks)
}