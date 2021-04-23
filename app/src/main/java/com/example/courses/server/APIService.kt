package com.example.courses.server

import com.example.courses.entity.Person
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("getPersons")
    suspend fun getPersons(): Response<List<Person>>

    @GET("getPersons")
    suspend fun getPersonsRX(): Observable<List<Person>>

    @POST("addPerson")
    suspend fun addPerson(@Body person: Person): Response<Person>
}