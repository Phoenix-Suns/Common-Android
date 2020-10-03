package com.nghiatl.common.extension

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(
            left ?: p.leftMargin,
            top ?: p.topMargin,
            right ?: p.rightMargin,
            bottom ?: p.bottomMargin)
        this.requestLayout()
    }
}

/**
 * Go to ScrollView Bottom
 * if (limitBottom <= 0) {
 *      scroll view is at bottom
 * }
 */
fun ScrollView.gotoBottom(): Int {
    return (getChildAt(0).bottom
            - height
            - scrollY)
}