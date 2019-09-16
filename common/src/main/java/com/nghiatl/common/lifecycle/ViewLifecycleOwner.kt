package com.nghiatl.common.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.nghiatl.common.lifecycle.LifeRegistry

class ViewLifecycleOwner : LifecycleOwner {
    private val mRegistry = LifeRegistry(this)

    override fun getLifecycle(): LifeRegistry {
        return mRegistry
    }
}