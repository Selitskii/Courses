package com.example.courses.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courses.Dependencies
import com.example.courses.domain.use_case.PersonDbUseCase
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.presentation.viewmodel.MainViewModel
/*
class Factory(val dbUseCase: PersonDbUseCase): ViewModelProvider.Factory {

    //private val personCloudUseCase: PersonsCloudUseCase by lazy { Dependencies.getPersonCloudUseCase() }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dbUseCase) as T
        } else throw Exception()
    }
}*/