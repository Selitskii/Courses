package com.example.clearav.domain.UseCase

class OperationUseCaseImpl(
    val operationReposytory: OperationReposytory
):OperationUseCase {
    override fun getOperation(): MutableList<Operation> {
        return  operationReposytory.getOperation()
    }

    override fun deleteOperation(position: Int) {
        operationReposytory.deleteOperation(position)
    }
}