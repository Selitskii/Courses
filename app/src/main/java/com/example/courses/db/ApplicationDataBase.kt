package com.example.courses.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courses.entity.Person

@Database(entities = [Person::class], version = 1)
abstract class ApplicationDataBase : RoomDatabase() {
    abstract fun getPersonDao(): PersonDao
}