package com.example.clearav.domain.UseCase

interface OperationUseCase {
    fun getOperation():MutableList<Operation>
}