package com.example.courses.UseCase

import com.example.courses.entity.Person

interface SharedPreferencesRepository {
    fun save(person: Person)
    fun take(): Person
}