package com.example.courses

import android.content.SharedPreferences
import com.example.courses.UseCase.*
import com.example.courses.data.SharedPreferencesLocalSouse
import com.example.courses.db.LocalDataBaseSource
import com.example.courses.server.CloudSource

object Dependencies {
    private val personRepository: PersonRepository by lazy { LocalDataBaseSource(App.instance) }
    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesLocalSouse(
            App.instance
        )
    }
    private val cloudDource: SimplifyPersonRepository by lazy { CloudSource() }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(personRepository, cloudDource)
    }

    fun getSharedPreferences(): SharedPreferencesUseCase {
        return SharedPreferencesUseCaseImpl(sharedPreferencesRepository)
    }
}