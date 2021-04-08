package com.example.clearav.domain.UseCase

class CalculateUseCaseImpl(
    val calculateRepository: CalculateRepository,
    val operationReposytory: OperationReposytory
    ):CalculateUseCase {
    override fun calculate(first: Int, second: Int):Int {
        val result=calculateRepository.calculate(Operation(first,second))
        val operation=Operation(first,second, result)
        operationReposytory.addOperation(operation)
        return  result
    }
}