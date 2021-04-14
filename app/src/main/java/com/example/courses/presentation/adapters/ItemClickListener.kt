package com.example.courses.presentation.adapters

import com.example.clearav.domain.UseCase.Operation

interface ItemClickListener {
    fun onClick(operation: Operation)
}