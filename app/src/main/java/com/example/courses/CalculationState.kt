package com.example.courses

sealed class CalculationState {

    object Free : CalculationState()
    object Loading : CalculationState()
    object Result : CalculationState()

}

