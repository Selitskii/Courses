package com.example.clearav.domain.UseCase

class OperationUseCaseImpl(
    val operationReposytory: OperationReposytory
):OperationUseCase {
    override fun getOperation(): MutableList<Operation> {
        return  operationReposytory.getOperation()
    }

    override fun deleteOperation(operation: Operation) {
        operationReposytory.deleteOperation(operation)
    }
}