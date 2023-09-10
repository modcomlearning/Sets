package com.lit.books.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
object ServiceBuilderLocal {
    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://coding.co.ke/digitich/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}