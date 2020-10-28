package com.nghiatl.common.extension

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView

fun View?.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    if (this?.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(
            left ?: p.leftMargin,
            top ?: p.topMargin,
            right ?: p.rightMargin,
            bottom ?: p.bottomMargin)
        this.requestLayout()
    }
}

fun View?.setPaddings(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) {
    this?.setPadding(
        start ?: this.paddingLeft,
        top ?: this.paddingTop,
        end ?: this.paddingRight,
        bottom ?: this.paddingBottom
    )
    this?.requestLayout()
}
