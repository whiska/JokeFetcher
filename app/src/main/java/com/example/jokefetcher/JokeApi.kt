package com.example.jokefetcher
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeApi {
    @Headers("Accept: application/json")
    @GET("/")
    suspend fun getJoke(): JokeResponse

}