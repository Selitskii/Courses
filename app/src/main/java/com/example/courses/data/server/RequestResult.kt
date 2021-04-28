package com.example.courses.data.server

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed class RequestResult<T>{

    class Error<T>(val exception:Throwable):RequestResult<T>()
    class Success<T>(val data:T):RequestResult<T>()

}

fun <T>Response<T>.toRequestResult():RequestResult<T>{
    return if(this.isSuccessful){
        val succeedResponse = this.body()
        if(succeedResponse==null){
            RequestResult.Error(Exception("Response body is null"))
        }else{
            RequestResult.Success(succeedResponse)
        }
    } else{
        RequestResult.Error(Exception(this.raw().message))
    }
}

/*
fun <T>Response<T>.doOnError():RequestResult<T>{
    val result = this.body()
}*/
