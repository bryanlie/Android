package com.example.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.weatherapp.R
import com.example.weatherapp.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val ZIP_CODE = "zipCode"
        const val DEFALUT_ZIP = 23188L
    }

    private var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE, DEFALUT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cityCode.setText(zipCode.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        zipCode = cityCode.text.toString().toLong()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }
}