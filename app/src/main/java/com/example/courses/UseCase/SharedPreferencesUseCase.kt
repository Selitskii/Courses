package com.example.courses.UseCase

import com.example.courses.entity.Person

interface SharedPreferencesUseCase {
    fun save(person: Person)
    fun take(): Person
}