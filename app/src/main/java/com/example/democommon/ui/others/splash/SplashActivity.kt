package com.example.democommon.ui.others.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democommon.R
import com.example.democommon.ui.common_library.samples.CommonLibrarySamplesActivity
import com.nghiatl.common.extension.delayFinishActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayFinishActivity(0.3F, CommonLibrarySamplesActivity::class.java)
    }
}
