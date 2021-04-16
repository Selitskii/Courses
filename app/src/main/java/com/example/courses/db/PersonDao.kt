package com.example.courses.db

import androidx.room.*
import com.example.courses.entity.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Delete
    fun delete(person: Person)

    @Query("Select * From Person")
    fun selectAll(): Flow<List<Person>>


}