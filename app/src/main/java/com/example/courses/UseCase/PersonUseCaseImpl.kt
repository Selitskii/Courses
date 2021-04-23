package com.example.courses.UseCase

import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    val personRepository: PersonRepository,
    val simplifyPersonRepository:SimplifyPersonRepository
) : PersonUseCase {
    override suspend fun subscribeToPerson(): List<Person> {
        return simplifyPersonRepository.getPersonsDatabase()
    }

    override suspend fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
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
}