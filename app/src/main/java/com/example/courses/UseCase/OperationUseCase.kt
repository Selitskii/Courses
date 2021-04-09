package com.example.clearav.domain.UseCase

import java.text.FieldPosition

interface OperationUseCase {
    fun getOperation():MutableList<Operation>
    fun deleteOperation(position: Int)
}