package com.example.courses.domain.repositories

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    suspend fun addPersonDB(person:Person)
    suspend fun removePersonDB(person: Person)
    suspend fun getPersonsDB(): Flow<List<Person>>
    fun getPersonsRXDB(): Observable<List<Person>>

}