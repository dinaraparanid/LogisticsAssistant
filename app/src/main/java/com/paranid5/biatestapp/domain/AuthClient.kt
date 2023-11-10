package com.paranid5.biatestapp.domain

import com.paranid5.biatestapp.data.retrofit.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Url placeholder
private const val AUTH_URL = "https://bebra.ru/"

interface AuthClient {
    @GET("/users")
    suspend fun getUserPasswordByPhoneNumber(@Query("phone_number") phoneNumber: String): Response<User>
}

val authRetrofit
    get() = Retrofit.Builder()
        .baseUrl(AUTH_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()