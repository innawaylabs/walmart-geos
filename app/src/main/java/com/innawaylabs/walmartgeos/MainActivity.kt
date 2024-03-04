package com.innawaylabs.walmartgeos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val LOG_TAG: String? = "RAVI_GEOS"
    // private val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    private val BASE_URL: String = "https://gist.githubusercontent.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchCountries()
    }

    private fun fetchCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d(LOG_TAG, retrofit.baseUrl().toString())

        val service = retrofit.create(CountryService::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.countriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        service.listCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    recyclerView.adapter = CountriesAdapter(response.body() ?: emptyList())
                } else {
                    Log.e(LOG_TAG, "API call unsuccessful:" + response.message().toString());
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.e(LOG_TAG, "Request failed", t)
                Log.e(LOG_TAG, t.localizedMessage)
            }
        })
    }
}

data class Country(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)
