package com.innawaylabs.walmartgeos.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innawaylabs.walmartgeos.domain.model.Country
import com.innawaylabs.walmartgeos.domain.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {
    private val repository = CountryRepository()
    val countries: MutableLiveData<List<Country>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun fetchCountries() {
        viewModelScope.launch {
            when (val result = repository.fetchCountries()) {
                repository.fetchCountries().onSuccess { countries ->
                    this@CountryViewModel.countries.postValue(countries)
                }.onFailure { exception ->
                    errorMessage.postValue(exception.message ?: "An error occurred")
                } -> Unit
            }
        }
    }
}