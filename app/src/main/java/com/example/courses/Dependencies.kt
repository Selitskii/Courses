package com.example.courses

import com.example.courses.data.db.PersonData
import com.example.courses.data.repositories.PersonRepository
import com.example.courses.domain.entity.PersonUseCaseImpl
import com.example.courses.domain.use_case.PersonUseCase

object Dependencies {

    private val personRepository: PersonRepository by lazy { PersonData(App.instance) }

    fun getPersonUseCase():PersonUseCase{
        return PersonUseCaseImpl(personRepository)
    }

}