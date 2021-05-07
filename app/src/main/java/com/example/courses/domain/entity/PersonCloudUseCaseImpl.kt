package com.example.courses.domain.entity

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.use_case.PersonWorkExecutor
import com.example.courses.domain.use_case.PersonsCloudUseCase
import javax.inject.Inject

class PersonCloudUseCaseImpl @Inject constructor(
    val personCloudRepository: PersonCloudRepository,
    ): PersonsCloudUseCase {
    override suspend fun getPersonsCL(): RequestResult<List<Person>> {
        return personCloudRepository.getPersonsCL()
    }

    override suspend fun getPersonsSM(): List<Person> {
        return personCloudRepository.getPersonSM()
    }

    override fun addPerson(name: String, rating: Int) {

    }

    override suspend fun addPersonCL(person: Person): RequestResult<Person> {
        return personCloudRepository.addPersonCL(person)
    }

}