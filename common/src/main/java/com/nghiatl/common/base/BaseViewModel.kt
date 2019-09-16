package com.nghiatl.common.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.nghiatl.common.event.LoadingEvent
import com.nghiatl.common.event.RefreshEvent
import com.nghiatl.common.event.SingleLiveEvent
import com.nghiatl.common.lifecycle.LifeRegistry
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), LifecycleOwner {
    val loading = LoadingEvent()
    val error = SingleLiveEvent<Throwable>()
    val refresh = RefreshEvent<Any>(this)

    private val mLife = LifeRegistry(this)
    private val mScope = ViewModelScope()

    override fun getLifecycle() = mLife

    init {
        mLife.create().start()
    }

    fun launch(
        loading: MutableLiveData<Boolean>? = this@BaseViewModel.loading,
        error: SingleLiveEvent<out Throwable>? = this@BaseViewModel.error,
        block: suspend CoroutineScope.() -> Unit
    ) = mScope.launch {
        try {
            loading?.postValue(true)
            block()
        } catch (e: CancellationException) {
            Log.i(this@BaseViewModel.javaClass.name, e.message ?: "Unknown")
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e("CALL_ERROR", "${e.javaClass.name} ${e.message ?: "Unknown"}")
            @Suppress("UNCHECKED_CAST")
            (error as? MutableLiveData<Throwable>)?.postValue(e)
        } finally {
            loading?.postValue(false)
        }
    }

    override fun onCleared() {
        mLife.stop().destroy()
        mScope.coroutineContext.cancel()
        System.gc()
    }

    private class ViewModelScope : CoroutineScope {
        override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
    }
}