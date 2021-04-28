package com.example.courses.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.Dependencies
import com.example.courses.data.server.RequestResult
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonDbUseCase
import com.example.courses.domain.use_case.PersonsCloudUseCase
import kotlinx.coroutines.launch

class MainViewModel(val personDbUseCase: PersonDbUseCase
    ) : ViewModel() {

/*
    private val personUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
*/

    private val personCloudUseCase: PersonsCloudUseCase by lazy { Dependencies.getPersonCloudUseCase() }


    var name: String = ""
    var rating: String = ""

    var persons = MutableLiveData<List<Person>>(listOf())
    private val topPersons = MutableLiveData<List<Person>>(listOf())
    private var personsFilter = MutableLiveData<List<Person>>(listOf())
    private val error = MutableLiveData<String>()
    private val personDataReady = MutableLiveData<Pair<String,Int>>()

    init {
        updatePerosn()
    }


    fun getTopPersons(): LiveData<List<Person>> {
        return topPersons
    }

    fun getPersonsVM(): LiveData<List<Person>> {
        return persons
    }

    fun getPersonsVMFilter(): LiveData<List<Person>> {
        return personsFilter
    }

    fun getError():LiveData<String> = error

    fun getPersonDataReady():LiveData<Pair<String,Int>> = personDataReady

    fun nameFilter() {

        /*viewModelScope.launch {
            personUseCase.observePersons().map { list ->
                list.sortedBy { it.name }
            }.collect {
                personsFilter.value = it
            }
        }*/

        personsFilter.value = persons.value!!.sortedBy { it.name }

    }

    fun ratingFilter() {

        /*viewModelScope.launch {
            personUseCase.observePersons().map { list ->
                list.sortedBy { it.rating }
            }.collect {
                personsFilter.value = it
            }
        }*/

        personsFilter.value = persons.value!!.sortedBy { it.rating }
    }

    fun addPersonVM() {
       val rating = try {
           this.rating.toInt()
       }catch (exception:Exception){
           0
       }
        personDataReady.value = name to rating
    }

    fun removePerson(person: Person) {
        viewModelScope.launch {
            personDbUseCase.deletePersonDB(person)
        }
    }

    private fun <T> processNetworkResult(
        requestResult:RequestResult<T>,
        action:(T) -> Unit
    ){
        when(requestResult){
            is RequestResult.Error ->{
                error.value = requestResult.exception.message
            }
            is RequestResult.Success -> {
                action(requestResult.data)
            }
        }
    }

    fun updatePerosn(){
        viewModelScope.launch {
            processNetworkResult(personCloudUseCase.getPersonsCL()) {
                persons.value = it
            }
        }
    }



}