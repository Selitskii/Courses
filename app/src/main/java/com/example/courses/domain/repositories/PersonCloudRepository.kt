package com.example.courses.domain.repositories

import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person

interface PersonCloudRepository {

    suspend fun getPersonsCL():RequestResult<List<Person>>
    suspend fun addPersonCL(person:Person):RequestResult<Person>

}