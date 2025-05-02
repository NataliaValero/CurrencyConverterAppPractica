package com.example.ejerciciopracticoconversion.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.ejerciciopracticoconversion.data.Convert
import com.example.ejerciciopracticoconversion.data.api.RetrofitClient
import com.example.ejerciciopracticoconversion.data.repository.MainInterfaceImpl
import com.example.ejerciciopracticoconversion.databinding.ActivityMainBinding
import com.example.ejerciciopracticoconversion.presentation.ConverterViewModel
import com.example.ejerciciopracticoconversion.presentation.CurrencyViewModel
import com.example.ejerciciopracticoconversion.presentation.CurrencyViewModelFactory
import com.example.ejerciciopracticoconversion.util.DispatcherProvider
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ConverterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View model factory
        val factory = CurrencyViewModelFactory(MainInterfaceImpl(RetrofitClient.currencyApiService))
        val viewModel = ViewModelProvider(this, factory).get(CurrencyViewModel::class.java)


        with(binding) {
            btnConvert.setOnClickListener {
                viewModel.convert(
                    etAmount.text.toString(),
                    spFrom.selectedItem.toString(),
                    spTo.selectedItem.toString()
                )
            }
        }

        with(binding) {

            lifecycleScope.launch {
                viewModel.conversion.collect { event ->
                    when (event) {
                        CurrencyViewModel.CurrencyEvent.Empty -> {
                            progressBar.isVisible = false
                        }

                        is CurrencyViewModel.CurrencyEvent.Failure -> {
                            progressBar.isVisible = false
                            tvResult.setTextColor(Color.RED)
                            tvResult.text = event.errorText
                        }

                        CurrencyViewModel.CurrencyEvent.Loading -> {
                            progressBar.isVisible = true
                        }

                        is CurrencyViewModel.CurrencyEvent.Success -> {
                            progressBar.isVisible = false
                            tvResult.setTextColor(Color.BLACK)
                            tvResult.text = event.resultText
                        }
                    }
                }
            }
        }
    }
}