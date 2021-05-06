package com.example.courses.data.db

import androidx.room.*
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(persons: List<Person>)

    @Delete
    fun delete(person: Person)

    @Query("Select * from Person ")
    fun selectAll(): Flow<List<Person>>

    @Query("Select * From Person")
    fun selectAllRX(): Observable<List<Person>>
}