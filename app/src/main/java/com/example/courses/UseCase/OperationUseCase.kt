package com.example.clearav.domain.UseCase

import java.text.FieldPosition

interface OperationUseCase {
    suspend fun getOperation():MutableList<Operation>
    suspend fun deleteOperation(operation: Operation)
}