package com.example.courses.domain.entity

import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.repositories.SimplifyPersonRepository
import com.example.courses.domain.use_case.PersonUseCase
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    val personRepository: PersonRepository,
    val simplifyPersonRepository: SimplifyPersonRepository
) : PersonUseCase {
    override suspend fun subscribePersons(): Flow<List<Person>> {
        return personRepository.subscribePersons()
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override fun getPersonsRX(): Observable<List<Person>> {
        return personRepository.getPersonsRX()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        simplifyPersonRepository.addPerson(name, rating)
    }

    override suspend fun getPersons(): List<Person> {
        return simplifyPersonRepository.getPersons()
    }
}