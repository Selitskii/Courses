package com.example.courses.db

import android.content.Context
import androidx.room.Room
import com.example.courses.UseCase.PersonRepository
import com.example.courses.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.toList

class LocalDataBaseSource(context: Context) : PersonRepository {

    val db = Room.databaseBuilder(
        context,
        ApplicationDataBase::class.java,
        "personDataBase"
    )
        .build()

    override suspend fun getPersons(): List<Person> {
        return emptyList()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        db.getPersonDao().insert(Person(name, rating))
    }

    override suspend fun removePerson(person: Person) {
        db.getPersonDao().delete(person)
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return db.getPersonDao().selectAllRx().share()
    }

    override suspend fun subscribePersons(): Flow<List<Person>> {
        return db.getPersonDao().selectAll()
    }
}