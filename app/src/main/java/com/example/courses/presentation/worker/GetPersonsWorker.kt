package com.example.courses.presentation.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.courses.Dependencies
import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.presentation.services.GetPersonService
import com.example.courses.presentation.viewmodel.MainViewModel
import io.reactivex.Scheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetPersonsWorker(private val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val personCloudUseCase: PersonsCloudUseCase = Dependencies.getPersonCloudUseCase()
    private val ioScope = CoroutineScope(Dispatchers.Main + Job())

    override suspend fun doWork(): Result {
          /*Intent(context, GetPersonService::class.java).also {
              JobIntentService.enqueueWork(
                  context,
                  GetPersonService::class.java,
                  123,
                  it
              )
          }*/


        val result: Result

            result = if (personCloudUseCase.getPersonsCL() is RequestResult.Success) {
                Toast.makeText(context,"YYYYYRRRRRAAAA!",Toast.LENGTH_SHORT).show()
                Result.success()
            } else Result.failure()
        return result
    }

}