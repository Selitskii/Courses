package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    private val personRepository: PersonRepository,
    private val simplifyPersonRepository: SimplifyPersonRepository

) : PersonUseCase {
    override suspend fun subscribePersons(): Flow<List<Person>> {
        return personRepository.subscribePersons()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        simplifyPersonRepository.addPerson(name, rating)
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }

    override suspend fun getPersons(): List<Person> {
        return simplifyPersonRepository.getPersons()
    }
}