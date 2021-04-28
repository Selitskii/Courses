package com.example.courses.domain.entity

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.use_case.PersonDbUseCase
import com.example.courses.domain.use_case.PersonsCloudUseCase

class PersonCloudUseCaseImpl(
    val personCloudRepository: PersonCloudRepository
    ): PersonsCloudUseCase {
    override suspend fun getPersonsCL(): RequestResult<List<Person>> {
        return personCloudRepository.getPersonsCL()
    }

    override suspend fun addPersonCL(person: Person): RequestResult<Person> {
        return personCloudRepository.addPersonCL(person)
    }

}