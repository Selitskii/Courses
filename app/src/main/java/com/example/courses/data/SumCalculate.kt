package com.example.clearav.data

import com.example.clearav.domain.UseCase.CalculateRepository
import com.example.clearav.domain.UseCase.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SumCalculate : CalculateRepository {
    override suspend fun calculate(operation: Operation): Int {
        //delay(5000)
        var sum = 0
        withContext(Dispatchers.IO)
        {
            for (i in 0..Int.MAX_VALUE) {
                if (sum % 2 == 0) {
                    sum += i
                } else {
                    sum -= i
                }
            }
        }


        return operation.first + operation.second
    }
}