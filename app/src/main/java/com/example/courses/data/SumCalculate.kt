package com.example.clearav.data

import com.example.clearav.domain.UseCase.CalculateRepository
import com.example.clearav.domain.UseCase.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SumCalculate : CalculateRepository {
    override suspend fun calculate(operation: Operation): Int {

        return operation.first + operation.second
    }
}