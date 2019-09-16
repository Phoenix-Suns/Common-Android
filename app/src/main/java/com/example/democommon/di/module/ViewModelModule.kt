package com.example.democommon.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.democommon.app.AppViewModelFactory
import com.example.democommon.app.ViewModelKey
import com.example.democommon.ui.account.login2.Login2ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun viewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(Login2ViewModel::class)
    protected abstract fun login2ViewModel(viewModel: Login2ViewModel): ViewModel
}