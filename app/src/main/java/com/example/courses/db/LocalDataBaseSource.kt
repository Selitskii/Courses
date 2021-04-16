package com.example.courses.db

import android.content.Context
import androidx.room.Room
import com.example.courses.UseCase.PersonRepository
import com.example.courses.entity.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.toList

class LocalDataBaseSource(context: Context) : PersonRepository {

    val db = Room.databaseBuilder(
        context,
        ApplicationDataBase::class.java,
        "personDataBase"
    )
        .allowMainThreadQueries().build()

    override suspend fun getPersons(): Flow<List<Person>> {
        return db.getPersonDao().selectAll()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        db.getPersonDao().insert(Person(name, rating))
    }

    override suspend fun removePerson(person: Person) {
        db.getPersonDao().delete(person)
    }
}