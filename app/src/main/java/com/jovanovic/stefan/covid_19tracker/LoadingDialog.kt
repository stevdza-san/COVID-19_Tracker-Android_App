package com.jovanovic.stefan.covid_19tracker

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(private var activity: Activity) {

    private lateinit var alertDialog: AlertDialog
    private val builder = AlertDialog.Builder(activity)

    fun startLoadingDialog(){
        val layoutInflater = activity.layoutInflater
        builder.setCancelable(false)
        builder.setView(layoutInflater.inflate(R.layout.custom_loading_dialog, null))
        alertDialog = builder.show()
    }

}