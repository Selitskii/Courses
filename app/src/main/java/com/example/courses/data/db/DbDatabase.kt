package com.example.courses.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courses.domain.entity.Person

@Database(entities = [Person::class], version = 1)
abstract class DbDatabase : RoomDatabase(), GetPersonDao