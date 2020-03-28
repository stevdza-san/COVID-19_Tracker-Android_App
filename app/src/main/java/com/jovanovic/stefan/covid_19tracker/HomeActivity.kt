package com.jovanovic.stefan.covid_19tracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var lastUpdate: String
    private lateinit var country: String
    private lateinit var activeCases: String
    private lateinit var recoveredCases: String
    private lateinit var fatalCases: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Get and Set Values
        getAndSetValues()
        supportActionBar?.title = country

    }

    private fun getAndSetValues(){
        lastUpdate = intent.getStringExtra("lastUpdate").replace("-", "/")
        country = intent.getStringExtra("country")
        activeCases = intent.getStringExtra("active")
        recoveredCases = intent.getStringExtra("recovered")
        fatalCases = intent.getStringExtra("fatal")

        val lastUpdateText = "Last Update:\n"
        country_text.text = country
        last_update_text.text = lastUpdateText.plus(lastUpdate)
        active_text_number.text = activeCases
        recovered_text_number.text = recoveredCases
        fatal_text_number.text = fatalCases
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.share_menu){
            val shareApp = Intent()
            shareApp.action = Intent.ACTION_SEND
            shareApp.putExtra(
                Intent.EXTRA_TEXT,
                "COVID-19 Statistics for $country: \nActive cases: $activeCases \nRecovered cases: $recoveredCases \nFatal cases: $fatalCases"
                + "\n\nLast Update Check: \n$lastUpdate"
            )
            shareApp.type = "text/plain"
            startActivity(shareApp)
        }else {
            return super.onOptionsItemSelected(item)
        }
        return true

    }
}
