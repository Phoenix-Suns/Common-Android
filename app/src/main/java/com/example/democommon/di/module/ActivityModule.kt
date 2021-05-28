package com.example.democommon.di.module

import com.example.democommon.ui.account.login_mvp.LoginActivity
import com.example.democommon.ui.account.login_mvvm.Login2Activity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeLogin2Activity(): Login2Activity
}