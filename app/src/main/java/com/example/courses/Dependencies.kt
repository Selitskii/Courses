package com.example.courses

import android.content.SharedPreferences
import com.example.courses.UseCase.*
import com.example.courses.data.SharedPreferencesLocalSouse
import com.example.courses.db.LocalDataBaseSource

object Dependencies {
    private val personRepository: PersonRepository by lazy { LocalDataBaseSource(App.instance) }
    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesLocalSouse(
            App.instance
        )
    }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(personRepository)
    }

    fun getSharedPreferences(): SharedPreferencesUseCase {
        return SharedPreferencesUseCaseImpl(sharedPreferencesRepository)
    }
}