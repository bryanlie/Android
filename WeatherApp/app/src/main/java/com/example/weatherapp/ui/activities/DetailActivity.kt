package com.example.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.example.weatherapp.R
import com.example.weatherapp.domain.commands.RequestDayForecastCommand
import com.example.weatherapp.domain.model.Forecast
import com.example.weatherapp.extensions.color
import com.example.weatherapp.extensions.textColor
import com.example.weatherapp.extensions.toDateString
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_forecast.icon
import kotlinx.android.synthetic.main.item_forecast.maxTemp
import kotlinx.android.synthetic.main.item_forecast.minTemp
import java.text.DateFormat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.jetbrains.anko.find

class DetailActivity : CoroutineScopeActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar)}

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        launch {
            val id = intent.getLongExtra(ID, -1)
            val result = RequestDayForecastCommand(id).execute()
            bindForecast(result)
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.get().load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemp, low to minTemp)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}