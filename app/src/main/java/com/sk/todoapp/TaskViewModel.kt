package com.sk.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepo
    var allTasks: LiveData<MutableList<Tasks>>

    init {
        val taskDao = TaskDb.getInstance(application)?.taskDao()
        repository = taskDao?.let { TaskRepo(it) }!!
        allTasks = repository.allTasks
    }

    fun insert(task: Tasks) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun update(task: Tasks) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun delete(task: Tasks) = viewModelScope.launch {
        repository.deleteTask(task)
    }
}
