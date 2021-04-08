package com.example.clearav.data

import com.example.clearav.domain.UseCase.CalculateRepository
import com.example.clearav.domain.UseCase.Operation

class SumCalculate:CalculateRepository {
    override fun calculate(operation: Operation): Int {
        return operation.first+operation.second
    }
}