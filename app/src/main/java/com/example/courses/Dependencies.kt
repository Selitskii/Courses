package com.example.courses

import com.example.clearav.data.OperationLocalSource
import com.example.clearav.data.SumCalculate
import com.example.clearav.domain.UseCase.*

object Dependencies {

    private val operationsRepository: OperationReposytory by lazy { OperationLocalSource() }

    fun getCalculateRepository():CalculateRepository{
        return SumCalculate()
    }

    fun getOperationReposytory():OperationReposytory{
        return operationsRepository
    }
    fun getCalculateUseCase():CalculateUseCase{
        return CalculateUseCaseImpl(getCalculateRepository(), getOperationReposytory())
    }

    fun getOperationUseCase():OperationUseCase{
        return OperationUseCaseImpl(getOperationReposytory())
    }
}