package com.example.courses.domain.use_case

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person

interface PersonsCloudUseCase {

    suspend fun addPersonCL(person:Person):RequestResult<Person>
    suspend fun getPersonsCL(): RequestResult<List<Person>>
    suspend fun getPersonsSM():List<Person>

}