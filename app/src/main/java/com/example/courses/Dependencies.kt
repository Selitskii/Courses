package com.example.courses

import android.content.Context
import com.example.courses.data.db.PersonData
import com.example.courses.data.server.CloudSource
import com.example.courses.domain.entity.PersonCloudUseCaseImpl
import com.example.courses.domain.entity.PersonDbUseCaseImpl
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.domain.use_case.PersonDbUseCase

object Dependencies {

    private val cloudSource: PersonCloudRepository by lazy { CloudSource() }

    private fun getPersonRepository(context:Context): PersonRepository{
        return PersonData(context)
    }

    fun getPersonUseCase(context: Context): PersonDbUseCase =
        PersonDbUseCaseImpl(getPersonRepository(context))

    fun getPersonCloudUseCase(): PersonsCloudUseCase =
        PersonCloudUseCaseImpl(cloudSource)

}