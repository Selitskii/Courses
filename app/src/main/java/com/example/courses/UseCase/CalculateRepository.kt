package com.example.clearav.domain.UseCase

interface CalculateRepository {
    fun calculate(operation: Operation):Int
}