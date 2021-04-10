package com.example.clearav.domain.UseCase

interface CalculateRepository {
    suspend fun calculate(operation: Operation):Int
}