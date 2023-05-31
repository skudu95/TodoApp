package com.sk.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 1)
abstract class TaskDb : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDb? = null

        fun getInstance(context: Context): TaskDb? {
            if (INSTANCE == null) {
                synchronized(TaskDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context, TaskDb::class.java, "tasks_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}