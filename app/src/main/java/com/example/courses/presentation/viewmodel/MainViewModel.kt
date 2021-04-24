package com.example.courses.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.Dependencies
import com.example.courses.domain.entity.Person
import com.example.courses.domain.use_case.PersonUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
            personUseCase.subscribePersons().map { list ->
                list.sortedBy { it.name }
            }.collect {
                personsFilter.value = it
            }
        }

    }

    fun ratingFilter() {

        viewModelScope.launch {
            personUseCase.subscribePersons().map { list ->
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
            persons.value = personUseCase.getPersons()

        }

    }

}