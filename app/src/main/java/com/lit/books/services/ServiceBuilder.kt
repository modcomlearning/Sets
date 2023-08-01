package com.lit.books.services

import androidx.appcompat.app.AppCompatActivity
import com.lit.books.BooksFragment
import com.lit.books.MainActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    //private val client = OkHttpClient.Builder().build()
        val prefs = BooksFragment.mainActivity.getSharedPreferences("storage", AppCompatActivity.MODE_PRIVATE)
        val Token = prefs.getString("Token", "")

        var okHttpClient = OkHttpClient.Builder().addInterceptor {chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $Token")
                .build()
            chain.proceed(newRequest)
        }

        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://coding.co.ke/sets/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun<T> buildService(service: Class<T>): T{
        println("This one222222222222 $Token")
        return retrofit.create(service)
    }
}