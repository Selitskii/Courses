package com.example.courses.UseCase

import com.example.courses.entity.Person
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {
    suspend fun getPersons(): Flow<List<Person>>
    suspend fun addPerson(name: String, rating: Int)
    suspend fun removePerson(person: Person)
}