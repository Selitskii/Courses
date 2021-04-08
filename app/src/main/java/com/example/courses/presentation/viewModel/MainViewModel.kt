package com.example.clearav.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clearav.domain.UseCase.CalculateUseCase
import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationUseCase
import com.example.courses.Dependencies
import java.text.FieldPosition

class MainViewModel : ViewModel() {
    private val calculateUseCase: CalculateUseCase by lazy { Dependencies.getCalculateUseCase() }
        private val operationUseCase: OperationUseCase by lazy { Dependencies.getOperationUseCase()}
    var first: String = ""
    var second: String = ""

    private var operations= MutableLiveData<MutableList<Operation>>(mutableListOf())

    fun getOperations():LiveData<MutableList<Operation>>{
        return operations
    }

    fun calculate(): Int {
        val rezult = calculateUseCase.calculate(first.toInt(), second.toInt())
        operations.value = operationUseCase.getOperation()
        return rezult
    }

    init {
        operations.value=operationUseCase.getOperation()
    }
    // TODO: Implement the ViewModel
}