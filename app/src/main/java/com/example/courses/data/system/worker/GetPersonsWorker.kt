package com.example.courses.data.system.worker

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.courses.Dependencies
import com.example.courses.data.server.RequestResult
import com.example.courses.domain.use_case.PersonsCloudUseCase

class GetPersonsWorker(private val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val personCloudUseCase: PersonsCloudUseCase = Dependencies.getPersonCloudUseCase()

    override suspend fun doWork(): Result {
        /*Intent(context, GetPersonService::class.java).also {
              JobIntentService.enqueueWork(
                  context,
                  GetPersonService::class.java,
                  123,
                  it
              )
          }*/


        return if (personCloudUseCase.getPersonsCL() is RequestResult.Success) {
            Toast.makeText(context, "YYYYYRRRRRAAAA!", Toast.LENGTH_SHORT).show()
            Result.success()
        } else Result.failure()
    }

}