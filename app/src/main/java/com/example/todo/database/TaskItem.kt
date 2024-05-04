package com.example.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,                     //add colum name -- @ColumnInfo(name = "name")
    val title: String,
    val details: String,
    val taskDate: Long,
)
{
    fun getDay(): String {
        return SimpleDateFormat("EEEE").format(Date(taskDate))
    }
    fun getDate(): String {
        return SimpleDateFormat("dd").format(Date(taskDate))
    }
    // get first 3 chars of the month
    fun getMonth(): String {
        return SimpleDateFormat("MMMM").format(Date(taskDate))
    }
    fun getFullDateString(): String {
        val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        return format.format(Date(taskDate))
    }
}

