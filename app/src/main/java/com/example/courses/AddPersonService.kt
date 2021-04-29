package com.example.courses

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.courses.Const.NAME
import com.example.courses.Const.RATING
import com.example.courses.Const.TAG
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonsCloudUseCase
import kotlinx.coroutines.*

class AddPersonService: Service() {

    private val personCloudUseCase:PersonsCloudUseCase = Dependencies.getPersonCloudUseCase()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    private val binder = PersonServiceBinder()


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "AddPersonService onStartCommand")
        val name = intent.getStringExtra(NAME) ?: ""
        val rating = intent.getIntExtra(RATING,0)

        startAddPersonProcess(name,rating)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }

    fun startAddPersonProcess(name:String,rating:Int){
        ioScope.launch {
            delay(5000)
            /*showNotification*/
            personCloudUseCase.addPersonCL(Person(name,rating))
            Intent().also { it.action = Const.ADD_PERSON_ACTION
                sendBroadcast(it)}}
        /*hideNoticfication*/
    }

    inner class PersonServiceBinder: Binder(){

        fun getService(): AddPersonService = this@AddPersonService

    }

}
