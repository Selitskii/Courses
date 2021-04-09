package com.example.clearav.data

import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationReposytory

class OperationLocalSource:OperationReposytory {
    private val operations = mutableListOf<Operation>(Operation(1,2,3))
    override fun getOperation(): MutableList<Operation> {
        return operations
    }

    override fun addOperation(operation: Operation) {
        operations.add(operation)
    }

    override fun deleteOperation(position: Int) {
        operations.removeAt(position)
    }
}