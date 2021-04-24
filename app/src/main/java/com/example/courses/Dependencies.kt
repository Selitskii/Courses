package com.example.courses

import com.example.courses.data.db.PersonData
import com.example.courses.data.server.CloudSource
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.entity.PersonUseCaseImpl
import com.example.courses.domain.repositories.SimplifyPersonRepository
import com.example.courses.domain.use_case.PersonUseCase

object Dependencies {

    private val personRepository: PersonRepository by lazy { PersonData(App.instance) }
    private val cloudSource:SimplifyPersonRepository by lazy { CloudSource() }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(personRepository, cloudSource)
    }


}