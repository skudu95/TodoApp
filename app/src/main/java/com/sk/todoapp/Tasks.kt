package com.sk.todoapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean
) : Parcelable