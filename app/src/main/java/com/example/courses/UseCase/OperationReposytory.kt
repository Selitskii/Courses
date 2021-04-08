package com.example.clearav.domain.UseCase

interface OperationReposytory {
    fun getOperation():MutableList<Operation>
    fun addOperation(operation: Operation)
}