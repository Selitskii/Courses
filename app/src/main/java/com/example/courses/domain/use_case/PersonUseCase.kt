package com.example.courses.domain.use_case

import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {

    suspend fun subscribePersons(): Flow<List<Person>>
    suspend fun addPerson(name: String, rating: Int)
    suspend fun removePerson(person: Person)
    fun getPersonsRX(): Observable<List<Person>>
    suspend fun getPersons():List<Person>

}