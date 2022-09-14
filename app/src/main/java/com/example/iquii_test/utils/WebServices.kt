package com.example.iquii_test.utils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url




private const val domain_name = "https://pokeapi.co/" //https://pokeapi.co/api/v2/pokemon/ditto
private const val sub = "api/v2/"
private const val BASE_URL = domain_name + sub

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WebServices {

    @GET("pokemon")
    fun getPokemon():
            Deferred<Response<String>>

    @GET
    fun getPoke(@Url url: String?):
            Deferred<Response<String>>
}

object Api {
    val WebService: WebServices by lazy {
        retrofit.create(WebServices::class.java)
    }
}
