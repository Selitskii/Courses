package com.example.courses.data.server

import com.example.courses.domain.entity.Person
import com.example.courses.domain.repositories.PersonCloudRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource:PersonCloudRepository {

    companion object{
        val baseUrl = "https://eu-api.backendless.com/E49B1F96-2BBB-4579-833F-7D3F5E6C84F8/E7A8D6D8-229A-4BFC-9801-F3A60E597E3B/services/Person/"
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    var apiService = retrofit.create(APIService::class.java)

    override suspend fun getPersonsCL(): RequestResult<List<Person>> {
        val personResponse:RequestResult<List<Person>>
        withContext(Dispatchers.IO){
            personResponse = apiService.getPersons().toRequestResult()
        }
        return personResponse
    }


    override suspend fun addPersonCL(person:Person): RequestResult<Person> {
        val personsResponse: RequestResult<Person>
        withContext(Dispatchers.IO){
            personsResponse = apiService.addPerson(person).toRequestResult()
        }
        return personsResponse
    }

}