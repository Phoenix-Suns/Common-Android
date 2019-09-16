package com.example.democommon.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import timber.log.Timber
import com.example.democommon.BuildConfig
import com.example.democommon.di.component.DaggerAppComponent
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.nghiatl.common.Prefs
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject
import kotlin.properties.Delegates

class MyApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingInjector
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initializeTimber()
        initializePrefs()
        initializeDI()
        initializeFacebook()
    }

    private fun initializeFacebook() {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    private fun initializeDI() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(AppTimberDebugTree())
        } else {
            Timber.plant(AppTimberReleaseTree())
        }
    }

    private fun initializePrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    companion object {
        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set
    }
}