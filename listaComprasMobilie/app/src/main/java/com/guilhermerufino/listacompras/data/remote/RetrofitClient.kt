package com.guilhermerufino.listacompras.data.remote

import com.guilhermerufino.listacompras.data.api.CategoriaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL= "https://kotlinjavaatividade.onrender.com"

    val api : CategoriaApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaApi::class.java)
    }
}