package com.example.courses.domain.entity

import com.example.courses.data.repositories.PersonRepository
import com.example.courses.domain.use_case.PersonUseCase
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    val personRepository: PersonRepository
) : PersonUseCase {
    override suspend fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        personRepository.addPerson(name,rating)
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override fun getPersonsRX(): Observable<List<Person>> {
        return personRepository.getPersonsRX()
    }
}