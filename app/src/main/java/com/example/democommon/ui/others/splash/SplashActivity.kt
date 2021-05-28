package com.example.democommon.ui.others.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democommon.R
import com.example.democommon.ui.account.login_mvvm.Login2Activity
import com.example.democommon.ui.common_library.samples.CommonLibrarySamplesActivity
import com.nghiatl.common.activity.ActivityUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ActivityUtil.delayFinishActivity(0.3F, this, CommonLibrarySamplesActivity::class.java)
    }
}
