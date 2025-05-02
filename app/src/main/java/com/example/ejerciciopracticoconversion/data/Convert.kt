package com.example.ejerciciopracticoconversion.data

class Convert {

    fun convert(amount: Double, from: Currency, to: Currency) : Double{

        // Paso 1: from -> to euro-
        val amountInEur = amount * ratesToEur[from]!!
        // Paso 2: euro-> to currency
        val result = amountInEur * ratesFromEur[to]!!

        return result
    }

    private val ratesToEur = mapOf(
        Currency.EUR to 1.0,
        Currency.USD to 1 / 1.14,   // USD ➔ EUR
        Currency.GPB to 1 / 0.86,   // GBP ➔ EUR
        Currency.JPY to 1 / 163.29  // JPY ➔ EUR
    )

    private val ratesFromEur = mapOf(
        Currency.EUR to 1.0,
        Currency.USD to 1.14,       // EUR ➔ USD
        Currency.GPB to 0.86,       // EUR ➔ GBP
        Currency.JPY to 163.29      // EUR ➔ JPY
    )



    enum class Currency(val code: String){
        EUR("EUR"),
        USD("USD"),
        GPB("GPB"),
        JPY("JPY")
    }
}
