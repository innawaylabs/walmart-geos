package com.innawaylabs.walmartgeos.domain.repository

import com.innawaylabs.walmartgeos.domain.model.Country
import com.innawaylabs.walmartgeos.network.CountryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {
    private val service: CountryService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryService::class.java)

    fun fetchCountries(success: (List<Country>) -> Unit, error: (String) -> Unit) {
        service.listCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    val validCountries = response.body()?.filter { it.name.isNotEmpty() } ?: emptyList()
                    if (validCountries.isNotEmpty()) {
                        success(validCountries)
                    } else {
                        error("No valid countries found.")
                    }
                } else {
                    error("API call unsuccessful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                error("Request failed: ${t.localizedMessage}")
            }
        })
    }

    companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com/"
    }
}