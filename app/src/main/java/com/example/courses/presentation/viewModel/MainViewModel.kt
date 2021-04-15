package com.example.clearav.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.Dependencies
import com.example.courses.UseCase.PersonUseCase
import com.example.courses.UseCase.SharedPreferencesUseCase
import com.example.courses.UseCase.SharedPreferencesUseCaseImpl
import com.example.courses.entity.Person
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val personUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    private val sharedPreferencesUseCase: SharedPreferencesUseCase by lazy { Dependencies.getSharedPreferences() }
    var first: String = ""
    var second: String = ""
    private var persons = MutableLiveData<List<Person>>(listOf())

    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun creat() {
        viewModelScope.launch {
            personUseCase.addPerson(first, second.toInt())
        }
    }

    fun save() {//name:String,rating:Int){
        sharedPreferencesUseCase.save(Person(first, second.toInt()))
    }

    fun take(): Person {
        return sharedPreferencesUseCase.take()
    }

    init {
        viewModelScope.launch {
            personUseCase.getPersons().collect {
                persons.value = it
            }
        }

    }


    fun onOperationSelected(person: Person) {
        viewModelScope.launch {
            personUseCase.removePerson(person)
        }
    }

}