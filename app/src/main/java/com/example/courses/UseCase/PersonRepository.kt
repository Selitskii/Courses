package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository:SimplifyPersonRepository {
    suspend fun removePerson(person: Person)
    suspend fun getPersons(): Flow<List<Person>>
    fun getPersonsRx(): Observable<List<Person>>
}

interface SimplifyPersonRepository{
    suspend fun getPersonsDatabase():List<Person>
    suspend fun addPerson(name: String, rating: Int)
}