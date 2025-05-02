package com.example.ejerciciopracticoconversion.data.api

import com.example.ejerciciopracticoconversion.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    suspend fun getRates(
        @Query("access_key") apiKey: String
    ) : Response<CurrencyResponse>


}