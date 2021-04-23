package com.example.courses.data.db

import android.content.Context
import androidx.room.Room
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonData(context: Context) : PersonRepository {

    private var database = Room.databaseBuilder(
        context, DbDatabase::class.java,
        "personDatabase"
    ).build()

    override suspend fun getPersons(): Flow<List<Person>> {
        return database.getPersonDao().selectAll()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        database.getPersonDao().insert(Person(name, rating))
    }

    override suspend fun removePerson(person: Person) {
        database.getPersonDao().delete(person)
    }

    override fun getPersonsRX(): Observable<List<Person>> {
        return database.getPersonDao().selectAllRX().share()
    }

}