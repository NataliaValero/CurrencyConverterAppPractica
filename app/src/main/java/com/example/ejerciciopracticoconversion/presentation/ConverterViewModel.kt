package com.example.ejerciciopracticoconversion.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejerciciopracticoconversion.data.Convert
import com.example.ejerciciopracticoconversion.data.repository.MainRepository

class ConverterViewModel: ViewModel() {


    private var _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    private var fromCurrency : Convert.Currency? = null
    private var toCurrency : Convert.Currency? = null


    // Crea instancia de convert
    private val converter = Convert()

    @SuppressLint("DefaultLocale")
    fun convert(amount: Double) {

        val result = converter.convert(amount, fromCurrency!!, toCurrency!!)
        val resultFormatted = String.format("%.1f", result)

        _result.value = "$amount ${fromCurrency!!.code} = $resultFormatted ${toCurrency!!.code}"
    }

    fun getFromCurrency(stringCode: String) {
        fromCurrency = findCurrency(stringCode)
    }

    fun getToCurrency(stringCode: String) {
        toCurrency = findCurrency(stringCode)
    }

    private fun findCurrency(stringCode: String) : Convert.Currency{
        var code = Convert.Currency.USD
        try {
            code = Convert.Currency.valueOf(stringCode)
        } catch (e: IllegalArgumentException){
            println("No matching enum value found for: $stringCode")
        }
        return code
    }

}