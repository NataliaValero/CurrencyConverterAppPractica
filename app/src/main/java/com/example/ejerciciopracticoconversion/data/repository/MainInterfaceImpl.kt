package com.example.ejerciciopracticoconversion.data.repository

import com.example.ejerciciopracticoconversion.data.api.ApiConfig
import com.example.ejerciciopracticoconversion.data.api.CurrencyApi
import com.example.ejerciciopracticoconversion.data.model.CurrencyResponse
import com.example.ejerciciopracticoconversion.util.Resource

class MainInterfaceImpl(private val apiService: CurrencyApi) : MainRepository {

    override suspend fun getRates(): Resource<CurrencyResponse> {
        return try {
            val response = apiService.getRates(ApiConfig.API_KEY)
            val result = response.body()

            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}