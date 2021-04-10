package com.example.courses.presentation.viewModel

sealed class CalculationState {

    object Free : CalculationState()
    object Loading : CalculationState()
    object Result : CalculationState()

}

