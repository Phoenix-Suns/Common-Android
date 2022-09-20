package com.nghiatl.common.cashreport

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.nghiatl.common.R


class CrashReportActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_report)

        val tvError = findViewById<View>(R.id.tvError) as TextView
        val btClose: Button = findViewById<View>(R.id.btClose) as Button
        tvError.text = intent.extras?.getString("error")

        btClose.setOnClickListener { intentData() }
    }

    override fun onBackPressed() {
        intentData()
        super.onBackPressed()
    }

    /**
     * Open New Activity
     */
    fun intentData() {
        /*Log.d("CDA", "onBackPressed Called")
        val setIntent = Intent(this, MainActivity::class.java)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)*/
    }
}