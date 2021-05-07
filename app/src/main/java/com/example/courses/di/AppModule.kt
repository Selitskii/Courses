package com.example.courses.di

import com.example.courses.data.server.CloudSource
import com.example.courses.data.server.CloudSource_Factory
import com.example.courses.domain.entity.PersonCloudUseCaseImpl
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.use_case.PersonsCloudUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCloudSource(): CloudSource = CloudSource()

    @Provides
    fun providePersonCloudUseCaseImpl(personCloudRepository: PersonCloudRepository): PersonCloudUseCaseImpl =
        PersonCloudUseCaseImpl(personCloudRepository)

    @Provides
    fun providePersonCloudRepository(cloudSource: CloudSource): PersonCloudRepository = cloudSource

    @Provides
    fun providePersonClouduseCase(personCloudUseCaseImpl: PersonCloudUseCaseImpl): PersonsCloudUseCase =
        personCloudUseCaseImpl
}