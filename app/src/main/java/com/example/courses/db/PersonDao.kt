package com.example.courses.db

import androidx.room.*
import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Delete
    fun delete(person: Person)

    @Query("Select * From Person")
    fun selectAll(): Flow<List<Person>>

    @Query("Select * From Person")
    fun selectAllRx(): Observable<List<Person>>

}