package com.nghiatl.common.extension

import android.app.Activity
import android.app.FragmentManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Activity.setFinishWhenNoExistFragment() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        this.fragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewDestroyed(fm: FragmentManager?, f: android.app.Fragment?) {
                super.onFragmentViewDestroyed(fm, f)

                if(fm?.fragments?.size == 0) {
                    finish()
                }
            }

        }, true)
    }
}

fun AppCompatActivity.setFinishWhenNoExistFragment() {
    this.supportFragmentManager.registerFragmentLifecycleCallbacks(
        object : androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewDestroyed(
                fm: androidx.fragment.app.FragmentManager,
                f: Fragment
            ) {
                super.onFragmentViewDestroyed(fm, f)

                if (fm.fragments.size == 0) {
                    finish()
                }
            }

        }, true
    )
}