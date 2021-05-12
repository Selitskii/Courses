package com.example.courses.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.courses.Dependencies
import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonDbUseCase
import com.example.courses.domain.use_case.PersonsCloudUseCase
import com.example.courses.presentation.worker.GetPersonsWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    val personDbUseCase: PersonDbUseCase,
    val personCloudUseCase:PersonsCloudUseCase
) : ViewModel() {

    //private val personCloudUseCase: PersonsCloudUseCase by lazy { Dependencies.getPersonCloudUseCase() }

    var name: String = ""
    var rating: String = ""

    var persons = MutableLiveData<List<Person>>(listOf())
    private var personsFilter = MutableLiveData<List<Person>>(listOf())
    private val error = MutableLiveData<String>()
    private val personDataReady = MutableLiveData<Pair<String, Int>>()

    init {
        updatePerosn()
        init()
    }

    fun getPersonsVM(): LiveData<List<Person>> {
        return persons
    }

    fun getPersonsVMFilter(): LiveData<List<Person>> {
        return personsFilter
    }

    fun getError(): LiveData<String> = error

    fun nameFilter() {

        viewModelScope.launch {
            personDbUseCase.getPersonsDB().collect {
                personsFilter.value = it.sortedBy {it.name }
            }
        }

    }

    fun ratingFilter() {

        viewModelScope.launch {
            personDbUseCase.getPersonsDB().collect {
                personsFilter.value = it.sortedBy { it.rating }
            }
        }

    }

    fun addPersonVM(){
        val rating = try {
            this.rating.toInt()
        } catch (exception: Exception) {
            0
        }
        personDataReady.value = name to rating
    }

    fun getPersonDataReady():MutableLiveData<Pair<String, Int>>{
        return personDataReady
    }

    fun removePerson(person: Person) {
        viewModelScope.launch {
            personDbUseCase.deletePersonDB(person)
        }
    }

    private fun <T> processNetworkResult(
        requestResult: RequestResult<T>,
        action: (T) -> Unit
    ) {
        when (requestResult) {
            is RequestResult.Error -> {
                error.value = requestResult.exception.message
            }
            is RequestResult.Success -> {
                action(requestResult.data)
            }
        }
    }

    fun updatePerosn() {
        /*  viewModelScope.launch {
              processNetworkResult(personCloudUseCase.getPersonsCL()) {
                  persons.value = it
              }

          }*/

        val uploadWork = OneTimeWorkRequestBuilder<GetPersonsWorker>().build()
        WorkManager.getInstance().enqueue(uploadWork)

    }

    fun init() {
        viewModelScope.launch {
            persons.value = personCloudUseCase.getPersonsSM()
            personDbUseCase.addListPerson(persons.value ?: listOf())
        }
    }

}