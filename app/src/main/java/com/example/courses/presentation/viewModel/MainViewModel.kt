package com.example.clearav.presentation.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.courses.Dependencies
import com.example.courses.UseCase.PersonUseCase
import com.example.courses.UseCase.SharedPreferencesUseCase
import com.example.courses.entity.Person
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel() : ViewModel() {

    private val personUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    private val personInputUseCase: SharedPreferencesUseCase by lazy { Dependencies.getSharedPreferences() }
    var first: String = ""
    var second: String = ""
    private var persons = MutableLiveData<List<Person>>(listOf())
    private var personsFilter = MutableLiveData<List<Person>>(listOf())
    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun getPersonsFilter(): LiveData<List<Person>> {
        return personsFilter
    }

    fun create() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                personUseCase.addPerson(first, second.toInt())
            }

        }
    }

    fun save() {
        personInputUseCase.save(Person(first, second.toInt()))
    }


    fun take(): Person {
        val person = personInputUseCase.take()
        first = person.name
        second = person.rating.toString()
        return person
    }

    init {
        val observable = personUseCase.getPersonsRx()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.d("ThreadName", Thread.currentThread().name)
            }
            .map { list ->
                list.sortedBy { it.name }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                persons.value = it
            }
        val observableFilter = personUseCase.getPersonsRx()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.filter { (it.rating > 10 && it.name.contains("Di")) }
                    .sortedBy { it.rating }
            }
            .filter {
                it.size >= 2
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                personsFilter.value = it
            }
    }


    fun onPersonSelected(person: Person) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                personUseCase.removePerson(person)
            }
        }
    }
}