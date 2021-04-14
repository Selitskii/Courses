package com.example.clearav.domain.UseCase

interface OperationReposytory {
    suspend fun getOperation(): MutableList<Operation>
    suspend fun addOperation(operation: Operation)
    suspend fun deleteOperation(operation: Operation)
}