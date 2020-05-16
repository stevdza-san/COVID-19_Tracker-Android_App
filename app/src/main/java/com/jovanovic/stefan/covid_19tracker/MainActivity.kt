package com.jovanovic.stefan.covid_19tracker

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var activeCases = ""
    var recoveredCases = ""
    var fatalCases = ""
    var lastUpdate = ""
    var chosenCountry = ""

    private lateinit var loadingDialog: LoadingDialog

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Autocomplete Text
        val countriesList = resources.getStringArray(R.array.countries)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countriesList)
        autoCompleteTextView.setAdapter(arrayAdapter)

        //Button Click Listener
        button.setOnClickListener(View.OnClickListener {

            if (validateInput() && isNetworkAvailable()) {
                //Loading Dialog
                loadingDialog = LoadingDialog(activity = this)
                loadingDialog.startLoadingDialog()

                //Request Data from API
                val myCountry = autoCompleteTextView.text.toString().trim().capitalize(Locale.ROOT)

                val requestAPIData = RequestAPIData(this)
                requestAPIData.requestData(myCountry)

            } else if (!isNetworkAvailable()) {
                snackBarMessage("No Internet Connection")
            } else {
                snackBarMessage("Invalid Country")
            }
        })


    }

    @ExperimentalStdlibApi
    fun validateInput(): Boolean {

        val countriesList = resources.getStringArray(R.array.countries).asList()
        val myCountry = autoCompleteTextView.text.toString()

        val validCountry = countriesList.find { it == myCountry.trim().capitalize(Locale.ROOT) }
        return validCountry != null

    }

    //Checking for Internet Connection
    private fun isNetworkAvailable(): Boolean {
        return try {
            val manager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo: NetworkInfo? = null
            networkInfo = manager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        } catch (e: NullPointerException) {
            false
        }
    }

    //Create Snackbar Message
    private fun snackBarMessage(message: String) {
        val snackbar = Snackbar.make(button, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(resources.getColor(R.color.ligter))
        snackbar.setAction("Try again", View.OnClickListener { })

        val snackbarView = snackbar.view
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(resources.getColor(R.color.white))
        textView.textSize = 16f
        snackbar.show()
    }

}
