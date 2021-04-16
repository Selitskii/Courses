package com.example.courses.UseCase

import com.example.courses.entity.Person

class SharedPreferencesUseCaseImpl(
    val sharedPreferencesRepository: SharedPreferencesRepository
) : SharedPreferencesUseCase {
    override fun save(person: Person) {
        sharedPreferencesRepository.save(person)
    }

    override fun take(): Person {
        return sharedPreferencesRepository.take()
    }

}