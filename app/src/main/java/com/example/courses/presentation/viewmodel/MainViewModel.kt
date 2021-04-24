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
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val personUseCase: PersonUseCase by lazy { Dependencies.getPersonUseCase() }
    var first: String = ""
    var second: String = ""
    private var persons = MutableLiveData<List<Person>>(listOf())
    private var personsFilter = MutableLiveData<List<Person>>(listOf())
    val compositeDispatcher = CompositeDisposable()

    init {
       getInit()
    }

    fun nameFilter() {

        val subscribeFilterName = personUseCase.getPersonsRX()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                list.sortedBy { it.name }
            }
            .subscribe {
                personsFilter.value = it
            }
        compositeDispatcher.add(subscribeFilterName)

    }

    fun ratingFilter() {

        val subscribeFilterRating = personUseCase.getPersonsRX()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                list.sortedBy { it.rating }
            }
            .subscribe {
                personsFilter.value = it
            }
        compositeDispatcher.add(subscribeFilterRating)

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

    fun destroy() {
        compositeDispatcher.dispose()
    }

    fun getInit(){
        val subscribe = personUseCase.getPersonsRX().subscribeOn(Schedulers.io()).doOnNext {
            Log.d("RXJAVA", Thread.currentThread().name)
        }.observeOn(AndroidSchedulers.mainThread()).doOnComplete {

        }.doOnError {

        }.subscribe {
            persons.value = it
        }
        compositeDispatcher.add(subscribe)
    }

}