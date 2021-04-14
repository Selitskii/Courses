package com.example.clearav.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clearav.domain.UseCase.CalculateUseCase
import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationUseCase
import com.example.courses.Dependencies
import com.example.courses.presentation.viewModel.CalculationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val calculateUseCase: CalculateUseCase by lazy { Dependencies.getCalculateUseCase() }
    private val operationUseCase: OperationUseCase by lazy { Dependencies.getOperationUseCase() }
    var first: String = ""
    var second: String = ""
    private val _calculationState = MutableLiveData<CalculationState>(CalculationState.Free)

    val calculationState: LiveData<CalculationState> = _calculationState


    private var operations = MutableLiveData<MutableList<Operation>>(mutableListOf())

    fun getOperations(): LiveData<MutableList<Operation>> {
        return operations
    }

    fun calculate(): Int {
        var rezult: Int = 0
        _calculationState.value = CalculationState.Loading

        viewModelScope.launch {
            rezult = calculateUseCase.calculate(first.toInt(), second.toInt())
            operations.value = operationUseCase.getOperation()
            _calculationState.value = CalculationState.Result
            setFree()
        }
        return rezult
    }

    init {
        viewModelScope.launch {
            operations.value = operationUseCase.getOperation()
        }
    }

    suspend fun setFree() {
        delay(3000)
        _calculationState.value = CalculationState.Free
    }

    fun onOperationSelected(operation: Operation) {
        viewModelScope.launch {
            operationUseCase.deleteOperation(operation)
            operations.value = operationUseCase.getOperation()
        }
    }
    // TODO: Implement the ViewModel
}