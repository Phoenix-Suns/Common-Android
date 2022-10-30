package com.nghiatl.common.extension

import android.graphics.PointF
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import kotlin.math.pow
import kotlin.math.sqrt

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

/**
 * Fragment Scale when Keyboard
 * Using:
 *
 *  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)

        // Fix keyboard scale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.window?.setDecorFitsSystemWindows(false)
            binding.root.addKeyboardVisibleListener { keyboardVisible ->
                val imeHeight = binding.root.rootWindowInsets?.getInsets(WindowInsets.Type.ime())?.bottom

                if (keyboardVisible) {
                    val navHeight =
                    binding.root.rootWindowInsets?.getInsets(WindowInsets.Type.navigationBars())?.bottom
                    binding.root.setPadding(0, 0, 0, (imeHeight ?: 0) - (navHeight ?: 0))
                } else {
                    binding.root.setPadding(0, 0, 0, (imeHeight ?: 0))
                }
        }
    }
    return binding.root
}

override fun onDestroyView() {
    // remove Fix keyboard scale
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        activity?.window?.setDecorFitsSystemWindows(true)
}

private var originalSoftInputMode: Int? = null

override fun onResume() {
    // Fix keyboard scale
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        this.originalSoftInputMode = activity?.window?.attributes?.softInputMode
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
    super.onResume()
}

override fun onPause() {
// remove Fix keyboard scale
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
        originalSoftInputMode?.let { activity?.window?.setSoftInputMode(it) }
    super.onPause()
}
 */
@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardVisibleListener(keyboardCallback: (keyboardVisible: Boolean) -> Unit) {
    doOnLayout {
        var keyboardVisible = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
        keyboardCallback(keyboardVisible)

        //whenever the layout resizes/changes, callback with the state of the keyboard.
        viewTreeObserver.addOnGlobalLayoutListener {
            val keyboardUpdateCheck = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
            //since the observer is hit quite often, only callback when there is a change.
            if (keyboardUpdateCheck != keyboardVisible) {
                keyboardCallback(keyboardUpdateCheck)
                keyboardVisible = keyboardUpdateCheck
            }
        }
    }
}

/** mContentView.contains(mTouchDown) **/
fun View.contains(point: PointF): Boolean {
    return left <= point.x && point.x <= right && top <= point.y && point.y <= bottom
}

/**
 * val event = MotionEvent()
 * mTouchDown.set(event.x, event.y)
 * mTouchDown.isClick(event)
 * **/
fun PointF.isClick(event: MotionEvent): Boolean {
    return sqrt(
        (x - event.x).toDouble().pow(2.0)
                + (y - event.y).toDouble().pow(2.0)
    ) <= 10
}
