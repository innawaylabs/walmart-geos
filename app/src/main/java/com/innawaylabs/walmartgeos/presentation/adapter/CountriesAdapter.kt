package com.innawaylabs.walmartgeos.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.innawaylabs.walmartgeos.R
import com.innawaylabs.walmartgeos.domain.model.Country

class CountriesAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryNameAndRegion: TextView = itemView.findViewById(R.id.tvCountryNameAndRegion)
        val countryCode: TextView = itemView.findViewById(R.id.tvCode)
        val capitalName: TextView = itemView.findViewById(R.id.tvCapital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val countryView = inflater.inflate(R.layout.country_item, parent, false)

        return ViewHolder(countryView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country: Country = countries[position]
        // Skip the comma if and when region is empty
        holder.countryNameAndRegion.text = if (country.region.isEmpty()) {
            country.name
        } else {
            holder.itemView.context.getString(
                R.string.display_country_name_and_region,
                country.name,
                country.region
            )
        }
        holder.countryCode.text = country.code
        holder.capitalName.text = country.capital
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}