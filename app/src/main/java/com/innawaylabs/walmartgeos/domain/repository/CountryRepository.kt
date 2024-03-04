package com.innawaylabs.walmartgeos.domain.repository

import com.innawaylabs.walmartgeos.domain.model.Country
import com.innawaylabs.walmartgeos.network.CountryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException

class CountryRepository(
    private val service: CountryService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryService::class.java)
) {
    suspend fun fetchCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val response = service.listCountries()
            if (response.isSuccessful) {
                Result.success(response.body()?.filter { it.name.isNotEmpty() } ?: emptyList())
            } else {
                Result.failure(Exception("API call unsuccessful: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("HTTP error: ${e.message}"))
        } catch (e: Throwable) {
            Result.failure(Exception("Request failed: ${e.localizedMessage}"))
        }
    }

    companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com/"
    }
}