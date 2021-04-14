package com.example.clearav.domain.UseCase

interface CalculateUseCase {

    suspend fun calculate(first: Int, second: Int): Int
}

