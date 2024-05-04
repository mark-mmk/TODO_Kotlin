package com.example.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoTasks {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(taskItem: TaskItem)
    @Query("SELECT * FROM tasks")   // ORDER BY id DESC
    fun getUsers(): List<TaskItem>
    @Delete
    fun deleteUsers(taskItem: TaskItem)
    @Update
    fun updateUsers(taskItem: TaskItem)
    @Query("SELECT * FROM Tasks WHERE id=:id")
    fun selectTaskByID(id:Int): TaskItem
}
