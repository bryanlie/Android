package com.example.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.Forecast
import com.example.weatherapp.domain.model.ForecastList
import com.example.weatherapp.extensions.toDateString
import com.example.weatherapp.extensions.ctx
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: ForecastList, private val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)

        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount() = weekForecast.size

    class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit): RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.get().load(iconUrl).into(itemView.icon)
                itemView.dateText.text = date.toDateString()
                itemView.descriptionText.text = description
                itemView.maxTemp.text = "$high"
                itemView.minTemp.text = "$low"
                itemView.setOnClickListener {itemClick(this)}
            }
        }
    }
}