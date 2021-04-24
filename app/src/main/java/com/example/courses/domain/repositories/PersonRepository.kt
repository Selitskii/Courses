package com.example.courses.domain.repositories

import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository: SimplifyPersonRepository {

    suspend fun subscribePersons(): Flow<List<Person>>
    suspend fun removePerson(person: Person)
    fun getPersonsRX(): Observable<List<Person>>

}

interface SimplifyPersonRepository{
    suspend fun getPersons():List<Person>
    suspend fun addPerson(name: String, rating: Int)
}