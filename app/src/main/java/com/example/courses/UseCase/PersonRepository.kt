package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository:SimplifyPersonRepository {
    suspend fun removePerson(person: Person)
    fun getPersonsRx(): Observable<List<Person>>
    suspend fun subscribePersons(): Flow<List<Person>>
}


interface SimplifyPersonRepository{
    suspend fun getPersons(): List<Person>
    suspend fun addPerson(name: String, rating: Int)
}