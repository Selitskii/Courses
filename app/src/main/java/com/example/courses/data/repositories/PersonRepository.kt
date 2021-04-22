package com.example.courses.data.repositories

import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    suspend fun getPersons(): Flow<List<Person>>
    suspend fun addPerson(name: String, rating: Int)
    suspend fun removePerson(person: Person)
    fun getPersonsRX(): Observable<List<Person>>

}