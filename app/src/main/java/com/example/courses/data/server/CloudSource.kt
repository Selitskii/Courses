package com.example.courses.data.server

import com.example.courses.domain.entity.Person
import com.example.courses.domain.repositories.SimplifyPersonRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource:SimplifyPersonRepository {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://eu-api.backendless.com/E49B1F96-2BBB-4579-833F-7D3F5E6C84F8/E7A8D6D8-229A-4BFC-9801-F3A60E597E3B/services/Person/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    var apiService = retrofit.create(APIService::class.java)

    override suspend fun getPersons():List<Person>{
        var result: List<Person>
        withContext(Dispatchers.IO){
            val requestResult = apiService.getPersons()
            result = requestResult.body()!!
        }
        return result
    }

    override suspend fun addPerson(name:String,rating:Int){
        withContext(Dispatchers.IO){
            val person = Person(name,rating)
            apiService.addPerson(person)
        }
    }

}