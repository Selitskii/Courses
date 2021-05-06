package com.example.courses.domain.entity

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.domain.use_case.PersonDbUseCase
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach

class PersonDbUseCaseImpl(
    val personRepository: PersonRepository,
) : PersonDbUseCase {
    override suspend fun getPersonsDB(): Flow<List<Person>> {
        return personRepository.getPersonsDB()
    }

    override fun getPersonsRXDB(): Observable<List<Person>> {
        return personRepository.getPersonsRXDB()
    }

    override suspend fun deletePersonDB(person: Person) {
        personRepository.removePersonDB(person)
    }

    override suspend fun addPersonDB(person: Person) {
        personRepository.addPersonDB(person)
    }

    override suspend fun addListPerson(list: List<Person>) {
        personRepository.addListPerson(list)
    }


}