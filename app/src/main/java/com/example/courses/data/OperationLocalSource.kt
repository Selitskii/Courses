package com.example.clearav.data

import kotlinx.coroutines.flow.Flow

import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationReposytory
import kotlinx.coroutines.flow.MutableSharedFlow


class OperationLocalSource : OperationReposytory {
    private val operationFlow: MutableSharedFlow<MutableList<Operation>> = MutableSharedFlow(replay =1)
    private val operations = mutableListOf<Operation>(Operation(1, 2, 3))
    override suspend fun getOperation() =operationFlow
        .apply {
            emit(operations)
    }


    override suspend fun addOperation(operation: Operation) {
        operations.add(operation)
        operationFlow.emit(operations)
    }

    override suspend fun deleteOperation(operation: Operation) {
        operations.remove(operation)
        operationFlow.emit(operations)
    }
}