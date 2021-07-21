package com.nghiatl.common.extension

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.io.Serializable

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

inline fun <reified T : Parcelable> BaseFragment.addParcelArgs(objects: T): BaseFragment {
    val args = Bundle()
    args.putParcelable(T::class.java.name, objects)
    arguments = args
    return this
}

fun BaseFragment.addArgs(newArgs: Bundle) : BaseFragment {
    var args = arguments
    if (args == null) args = Bundle()
    args.putAll(newArgs)
    arguments = args
    return this
}

fun Fragment.addArgs(newArgs: Bundle) {
    var args = arguments
    if (args == null) args = Bundle()
    args.putAll(newArgs)
    arguments = args
}

fun Fragment.addArgs(vararg newArgs: Pair<String, Serializable>) {
    var args = arguments
    if (args == null) args = Bundle()
    newArgs.forEach { args.putSerializable(it.first, it.second) }
    arguments = args
}