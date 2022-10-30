package com.nghiatl.common.extension

import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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

/**
 * Finish Activity after few second
 * @param delaySecond
 * @param toActivity
 */
fun Activity.delayFinishActivity(delaySecond: Float, toActivity: Class<*>?) {
    Handler(Looper.getMainLooper()).postDelayed({

        toActivity?.let {
            val mainIntent = Intent(this, toActivity)
            startActivity(mainIntent)
        }

        finish()
    }, (delaySecond * 1000).toLong())
}

/**
 * Đặt kết quả trả về Activity
 * @param activity
 * @param data
 */
fun Activity.setResultOk(data: Intent?) {
    if (this.parent == null) {
        this.setResult(Activity.RESULT_OK, data)
    } else {
        this.parent.setResult(Activity.RESULT_OK, data)
    }
}

/**
 * Cài đặt Toolbar trong Activity
 * @param toolbarId id của Toolbar
 * @param displayHomeAsUp
 * @param homeAsUpIndicatorId id ảnh của nút Home
 * @param displayIcon
 * @param iconId id ảnh của Icon
 */
fun AppCompatActivity.setupToolbar(
    toolbarId: Int,
    showTitle: Boolean,
    displayHomeAsUp: Boolean, homeAsUpIndicatorId: Int?,
    displayIcon: Boolean, iconId: Int?
) {
    // lấy Toolbar
    val toolbar = this.findViewById<View>(toolbarId) as Toolbar
    this.setSupportActionBar(toolbar)
    this.setupToolbar(
        showTitle,
        displayHomeAsUp,
        homeAsUpIndicatorId,
        displayIcon,
        iconId
    )
}

/**
 * Setup Toolbar for Fragment
 * @param showTitle
 * @param displayHomeAsUp
 * @param homeAsUpIndicatorId
 * @param displayIcon
 * @param iconId
 */
fun AppCompatActivity.setupToolbar(
    showTitle: Boolean,
    displayHomeAsUp: Boolean,
    homeAsUpIndicatorId: Int?,
    displayIcon: Boolean,
    iconId: Int?
) {
    val actionBar = this.supportActionBar
    if (actionBar != null) {
        // Show Title
        if (showTitle) actionBar.setDisplayShowTitleEnabled(true) else actionBar.setDisplayShowTitleEnabled(
            false
        )

        // hiện Button Home hay Up
        actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp) // hiển thị Home/ Up
        if (homeAsUpIndicatorId != null) actionBar.setHomeAsUpIndicator(homeAsUpIndicatorId) // đổi ảnh Home, Up

        // Hiện icon
        actionBar.setHomeButtonEnabled(displayIcon)
        if (iconId != null) actionBar.setIcon(ContextCompat.getDrawable(this, iconId))
    }
}