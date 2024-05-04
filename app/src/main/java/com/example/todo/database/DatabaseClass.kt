package com.example.todo.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun DaoTasks(): DaoTasks

    companion object {
        @Volatile
        private var INSTANCE: DatabaseClass? = null

        fun getDatabase(context: android.content.Context): DatabaseClass {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseClass::class.java,
                    "my_database_TODO"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}