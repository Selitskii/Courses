package com.example.courses.data.db

import android.content.Context
import androidx.room.Room
import com.example.courses.domain.repositories.PersonRepository
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PersonData(context: Context) : PersonRepository {

    private var database = Room.databaseBuilder(
        context, DbDatabase::class.java,
        "personDatabase"
    ).build()

    override suspend fun subscribePersons(): Flow<List<Person>> {
        return database.getPersonDao().selectAll()
    }

    override suspend fun addPerson(name: String, rating: Int) {
        withContext(Dispatchers.IO){
            database.getPersonDao().insert(Person(name, rating))
        }
    }

    override suspend fun removePerson(person: Person) {
        withContext(Dispatchers.IO){
            database.getPersonDao().delete(person)
        }
    }

    override fun getPersonsRX(): Observable<List<Person>> {
        return database.getPersonDao().selectAllRX().share()
    }

    override suspend fun getPersons(): List<Person> {
        return emptyList()
    }

}