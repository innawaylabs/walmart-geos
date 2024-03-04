package com.innawaylabs.walmartgeos.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innawaylabs.walmartgeos.R
import com.innawaylabs.walmartgeos.presentation.adapter.CountriesAdapter
import com.innawaylabs.walmartgeos.presentation.viewmodel.CountryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var rvCountries: RecyclerView
    private lateinit var tvErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCountries = findViewById<RecyclerView>(R.id.rvCountries).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        val viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.countries.observe(this) { countries ->
            rvCountries.visibility = View.VISIBLE
            tvErrorMessage.visibility = View.GONE
            rvCountries.adapter = CountriesAdapter(countries)
        }
        viewModel.errorMessage.observe(this) { message ->
            displayErrorMessage(message)
        }
        viewModel.fetchCountries()
    }

    private fun displayErrorMessage(message: String) {
        rvCountries.visibility = View.GONE
        tvErrorMessage.visibility = View.VISIBLE
        tvErrorMessage.text = message
        Log.e(LOG_TAG, message)
    }

    companion object {
        const val LOG_TAG: String = "MainActivity"
    }
}