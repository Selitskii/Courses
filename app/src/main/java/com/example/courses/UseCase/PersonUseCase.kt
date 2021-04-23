package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {
    suspend fun subscribeToPerson(): List<Person>
    suspend fun getPersons(): Flow<List<Person>>
    suspend fun addPerson(name: String, rating: Int)
    suspend fun removePerson(person: Person)
    fun getPersonsRx(): Observable<List<Person>>
}