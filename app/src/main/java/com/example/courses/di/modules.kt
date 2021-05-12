package com.example.courses.di

import com.example.courses.data.db.PersonData
import com.example.courses.data.server.CloudSource
import com.example.courses.domain.entity.PersonCloudUseCaseImpl
import com.example.courses.domain.entity.PersonDbUseCaseImpl
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.use_case.PersonDbUseCase
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.factoryBy

val appModule = module {
    single {
        CloudSource()
    }
    single {
        PersonData(androidContext())
    }
    factoryBy<PersonCloudRepository,CloudSource>()
    factoryBy<PersonRepository,PersonData>()
    factoryBy<PersonDbUseCase,PersonDbUseCaseImpl>()
    factoryBy<PersonsCloudUseCase,PersonCloudUseCaseImpl>()
    factory {
        PersonCloudUseCaseImpl(get())
    }
    factory {
        PersonDbUseCaseImpl(get())
    }
}

val viewModule = module {
    viewModel {
        MainViewModel(get(),get())
    }
}