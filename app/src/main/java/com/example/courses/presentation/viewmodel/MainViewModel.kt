package com.example.courses.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.Dependencies
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val personUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    var first: String = ""
    var second: String = ""
    private var persons = MutableLiveData<List<Person>>(listOf())
    private var personsFilter = MutableLiveData<List<Person>>(listOf())

    init {
        getInit()
    }

    fun nameFilter() {

        viewModelScope.launch {
            personUseCase.getPersons().map { list ->
                list.sortedBy { it.name }
            }.collect {
                personsFilter.value = it
            }
        }

    }

    fun ratingFilter() {

        viewModelScope.launch {
            personUseCase.getPersons().map { list ->
                list.sortedBy { it.rating }
            }.collect {
                personsFilter.value = it
            }
        }
    }

    fun getPersonsVM(): LiveData<List<Person>> {
        return persons
    }

    fun getPersonsVMFilter(): LiveData<List<Person>> {
        return personsFilter
    }

    fun addPersonVM() {
        viewModelScope.launch {
            if (first != "" && second != "") {
                personUseCase.addPerson(first, second.toInt())
            }
        }
    }

    fun removePerson(person: Person) {
        viewModelScope.launch {
            personUseCase.removePerson(person)
        }
    }

    fun getInit() {

        viewModelScope.launch {
            personUseCase.getPersons().collect {
                persons.value = it
            }

        }

    }

}