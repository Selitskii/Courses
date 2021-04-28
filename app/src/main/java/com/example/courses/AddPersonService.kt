package com.example.courses

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonsCloudUseCase
import kotlinx.coroutines.*

class AddPersonService: Service() {

    private val personCloudUseCase:PersonsCloudUseCase = Dependencies.getPersonCloudUseCase()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    companion object{
        const val TAG = "AddPersonService"
        const val NAME = "name"
        const val RATING = "rating"
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "AddPersonService onStartCommand")
        val name = intent.getStringExtra(NAME) ?: ""
        val rating = intent.getIntExtra(RATING,0)

        ioScope.launch { personCloudUseCase.addPersonCL(Person(name,rating)) }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }
}