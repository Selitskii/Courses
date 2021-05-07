package com.example.courses.data.server

import com.example.courses.BuildConfig
import com.example.courses.domain.entity.Person
import com.example.courses.domain.repositories.PersonCloudRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CloudSource @Inject constructor() :PersonCloudRepository {

    companion object{
        val baseUrl = "https://eu-api.backendless.com/E49B1F96-2BBB-4579-833F-7D3F5E6C84F8/E7A8D6D8-229A-4BFC-9801-F3A60E597E3B/services/Person/"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
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

    override suspend fun getPersonSM(): List<Person> {
        val persons:List<Person>
        withContext(Dispatchers.IO){
            persons = apiService.getPersonsSM()
        }
        return persons
    }

}