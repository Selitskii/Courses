package com.example.clearav.domain.UseCase

class OperationUseCaseImpl(
        val operationReposytory: OperationReposytory
) : OperationUseCase {
    override suspend fun getOperation(): MutableList<Operation> {
        return operationReposytory.getOperation()
    }

    override suspend fun deleteOperation(operation: Operation) {
        operationReposytory.deleteOperation(operation)
    }
}