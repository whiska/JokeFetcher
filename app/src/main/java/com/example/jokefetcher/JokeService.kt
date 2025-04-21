package com.example.jokefetcher

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JokeService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://icanhazdadjoke.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(JokeApi::class.java)
}