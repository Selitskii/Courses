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

    private var db = Room.databaseBuilder(
        context, DbDatabase::class.java,
        "personDatabase"
    ).build()

    private val personDao = db.getPersonDao()

    override suspend fun addPersonDB(person: Person) {
        withContext(Dispatchers.IO){
            personDao.insert(person)
        }
    }

    override suspend fun removePersonDB(person: Person) {
        withContext(Dispatchers.IO){
            personDao.delete(person)
        }
    }

    override suspend fun getPersonsDB(): Flow<List<Person>> {
        return personDao.selectAll()
    }

    override fun getPersonsRXDB(): Observable<List<Person>> {
        return personDao.selectAllRX()
    }


}