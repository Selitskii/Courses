package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    val personRepository: PersonRepository
) : PersonUseCase {
    override suspend fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        personRepository.addPerson(name, rating)
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }
}