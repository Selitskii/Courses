package com.example.clearav.domain.UseCase

import kotlinx.coroutines.flow.Flow

class OperationUseCaseImpl(
        val operationReposytory: OperationReposytory
) : OperationUseCase {
    override suspend fun getOperation(): Flow<MutableList<Operation>> {
        return operationReposytory.getOperation()
    }

    override suspend fun deleteOperation(operation: Operation) {
        operationReposytory.deleteOperation(operation)
    }
}