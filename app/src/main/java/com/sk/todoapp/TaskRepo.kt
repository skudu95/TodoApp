package com.sk.todoapp

import androidx.lifecycle.LiveData

class TaskRepo(private val taskDao: TaskDao) {
    val allTasks: LiveData<MutableList<Tasks>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Tasks) = taskDao.insertTask(task)

    suspend fun updateTask(task: Tasks) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Tasks) = taskDao.deleteTask(task)
}