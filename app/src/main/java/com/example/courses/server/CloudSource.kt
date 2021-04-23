package com.example.courses.server

import com.example.courses.UseCase.SimplifyPersonRepository
import com.example.courses.entity.Person
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource:SimplifyPersonRepository {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://eu-api.backendless.com/E49B1F96-2BBB-4579-833F-7D3F5E6C84F8/E7A8D6D8-229A-4BFC-9801-F3A60E597E3B/services/Person/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    val apiService: APIService = retrofit.create(APIService::class.java)


    override suspend fun getPersonsDatabase(): List<Person> {
        var Result:List<Person>
        withContext(Dispatchers.IO) {
            val requestResult = apiService.getPersons()
            Result=requestResult.body()!!
        }
        return Result
    }

    override suspend fun addPerson(name: String, rating: Int) {
        withContext(Dispatchers.IO){
            val person = Person(name,rating)
            apiService.addPerson(person)
        }
    }
}