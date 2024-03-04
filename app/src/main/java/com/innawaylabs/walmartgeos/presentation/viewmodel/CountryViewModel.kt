package com.innawaylabs.walmartgeos.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innawaylabs.walmartgeos.domain.model.Country
import com.innawaylabs.walmartgeos.domain.repository.CountryRepository

class CountryViewModel : ViewModel() {
    private val repository = CountryRepository()
    val countries: MutableLiveData<List<Country>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun fetchCountries() {
        repository.fetchCountries(success = {
            countries.postValue(it)
        }, error = {
            errorMessage.postValue(it)
        })
    }
}