package com.example.ejerciciopracticoconversion.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ejerciciopracticoconversion.data.repository.MainRepository
import com.example.ejerciciopracticoconversion.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class CurrencyViewModel(
    private val repository: MainRepository
) : ViewModel() {

    // Currency event clas
    sealed class CurrencyEvent {
        class Success( val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    // State flow
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion


    fun convert(amountStr: String, fromCurrency: String, toCurrency: String) {
        val fromAmount = amountStr.toFloatOrNull()

        if(fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = CurrencyEvent.Loading

            when(val ratesResponse = repository.getRates()) {
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.errorMessage!!)
                is Resource.Success -> {

                    val rates = ratesResponse.data!!.rates

                    val toRate = getRateForCurrency(toCurrency, rates)

                    if(toRate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unsupported currency: $toCurrency")
                    } else {
                        val convertedCurrency = round(fromAmount * toRate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Map<String, Double>): Double? {
        // Para obtener rate ejemplo aedRate = rates["AED"]
        return rates[currency]
    }
}



class CurrencyViewModelFactory(
    private val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}