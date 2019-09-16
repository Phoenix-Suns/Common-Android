package com.example.democommon.di.component

import android.app.Application
import com.example.democommon.app.MyApplication
import com.example.democommon.di.module.*
import com.example.democommon.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        DbModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MyApplication)
}