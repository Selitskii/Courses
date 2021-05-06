package com.example.courses.presentation.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.JobIntentService
import com.example.courses.Dependencies
import com.example.courses.domain.repositories.PersonCloudRepository
import com.example.courses.domain.use_case.PersonsCloudUseCase
import kotlinx.coroutines.*

class GetPersonService: JobIntentService() {

    private val personCloudUseCase: PersonsCloudUseCase  = Dependencies.getPersonCloudUseCase()
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onHandleWork(intent: Intent) {
        ioScope.launch {
            personCloudUseCase.getPersonsCL()
        }
    }

}