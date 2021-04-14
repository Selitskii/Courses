package com.example.clearav.domain.UseCase

import kotlinx.coroutines.flow.Flow
import java.text.FieldPosition

interface OperationUseCase {
    suspend fun getOperation(): Flow<MutableList<Operation>>
    suspend fun deleteOperation(operation: Operation)
}