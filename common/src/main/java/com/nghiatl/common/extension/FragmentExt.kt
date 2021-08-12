package com.nghiatl.common.extension

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

private fun Fragment.isParentVisible(): Boolean {
    var parent = parentFragment
    while (parent != null) {
        if (parent.isHidden) return false
        parent = parent.parentFragment
    }
    return true
}

fun Fragment.isVisibleInParent() = !isHidden && userVisibleHint

fun Fragment.isVisibleOnScreen() = isVisibleInParent() && isParentVisible()

fun Fragment.dispatchHidden(hidden: Boolean) = findChildVisible()?.onHiddenChanged(hidden)

fun Fragment.findChildVisible(): Fragment? {
    var childVisible = childFragmentManager.primaryNavigationFragment
    if (childVisible == null) {
        childVisible = childFragmentManager.fragments.find { it.isVisibleInParent() }
    }
    return childVisible
}

fun Fragment.finishFragment() {
    if (parentFragment is DialogFragment) {
        try {
            (parentFragment as DialogFragment?)?.dismissAllowingStateLoss()
        } catch (ex: Exception) {
            Log.e("Fragment.finishFragment","Can not close dialog ${ex.message}")
        }
    } else {
        if (isAdded) {
            activity?.onBackPressed()
        }
    }
}

/*
fun <T> Fragment.setNavigationResult(key: String, value: T) {
    findNavController()
        .previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}*/

/*fun <T> Fragment.getNavigationResult(key: String, onResult: (result: T?) -> Unit) {
    val navBackStackEntry = findNavController().currentBackStackEntry

    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry?.savedStateHandle?.contains(key) == true) {
            val result = navBackStackEntry.savedStateHandle.get<T>(key)
            onResult.invoke(result)
            navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry?.lifecycle?.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry?.lifecycle?.removeObserver(observer)
        }
    })
}*/
