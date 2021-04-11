package com.example.clearav.data

import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationReposytory

class OperationLocalSource:OperationReposytory {
    private val operations = mutableListOf<Operation>(Operation(1,2,3))
    override suspend fun getOperation(): MutableList<Operation> {
        return operations
    }

    override suspend fun addOperation(operation: Operation) {
        operations.add(operation)
    }

    override suspend fun deleteOperation(operation: Operation) {
        operations.remove(operation)
    }
}