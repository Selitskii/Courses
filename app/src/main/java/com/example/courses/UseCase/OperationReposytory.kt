package com.example.clearav.domain.UseCase

import kotlinx.coroutines.flow.Flow

interface OperationReposytory {
    suspend fun getOperation(): Flow<MutableList<Operation>>
    suspend fun addOperation(operation: Operation)
    suspend fun deleteOperation(operation: Operation)
}