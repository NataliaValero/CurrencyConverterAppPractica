package com.example.ejerciciopracticoconversion.data.repository


import com.example.ejerciciopracticoconversion.data.model.CurrencyResponse
import com.example.ejerciciopracticoconversion.util.Resource


interface MainRepository {
    suspend fun getRates() : Resource<CurrencyResponse>
}