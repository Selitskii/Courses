package com.example.courses.domain.use_case

import com.example.courses.domain.entity.Person

interface PersonWorkExecutor {

    fun addPerson(person:Person)

}