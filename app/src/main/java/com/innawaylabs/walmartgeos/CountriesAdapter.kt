package com.innawaylabs.walmartgeos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountriesAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

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
        // TBD: Handle empty strings
        val country: Country = countries[position]
        holder.countryNameAndRegion.text = country.name + ", " + country.region
        holder.countryCode.text = country.code
        holder.capitalName.text = country.capital
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}
