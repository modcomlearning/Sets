package com.lit.books.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilderLogin {
    //private val client = OkHttpClient.Builder().build()
    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:5000/")
        .baseUrl("https://coding.co.ke/digitich/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}