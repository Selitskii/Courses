package com.example.courses.domain.use_case

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonDbUseCase {

    suspend fun getPersonsDB(): Flow<List<Person>>
    fun getPersonsRXDB(): Observable<List<Person>>
    suspend fun deletePersonDB(person:Person)
    suspend fun addPersonDB(person:Person)
    suspend fun addListPerson(list:List<Person>)

}