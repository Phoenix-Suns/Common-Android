package com.nghiatl.common.extension

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import androidx.annotation.AnimRes
import kotlin.jvm.internal.Intrinsics

//#region Animation
private const val DEFAULT_DURATION: Long = 300

/**
 * Auto Show/Hide Animation
 * @param viewGroup
 * @param listener
 */
fun ViewGroup?.repairAutoAnimation(
    listener: Transition.TransitionListener?
) {
    val transition = AutoTransition()
    transition.duration = DEFAULT_DURATION
    transition.ordering = TransitionSet.ORDERING_TOGETHER
    if (listener != null) transition.addListener(listener)
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairFadeAnimation() {
    val transition = Fade()
    transition.duration = DEFAULT_DURATION
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairChangeBoundsAnimation() {
    val transition = ChangeBounds()
    transition.duration = DEFAULT_DURATION
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairSlideAnimation(slideUp: Boolean) {
    val transition = Slide(Gravity.BOTTOM)
    transition.duration = DEFAULT_DURATION
    TransitionManager.beginDelayedTransition(this, transition)
}

fun View?.runChangeColorAnimation(
    colorFrom: Int = Color.RED,
    colorTo: Int = Color.GREEN
) {
    ObjectAnimator.ofObject(
        this,
        "backgroundColor",
        ArgbEvaluator(),
        colorFrom,
        colorTo
    )
        .setDuration(DEFAULT_DURATION)
        .start()
}

fun View?.runScaleAnimation(): ScaleAnimation? {
    val animation = ScaleAnimation(
        1.15f, 1f, 1.15f, 1f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    this?.animation = animation
    animation.duration = 100
    animation.start()
    return animation
}

/**
 * Expand View Height
 */
fun View?.runExpandAnimation(duration: Long?): Animation {
    val initHeight = this?.height ?: 0
    this?.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = this?.measuredHeight ?: 0
    val distanceHeight = targetHeight - initHeight
    val animation: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            this@runExpandAnimation?.layoutParams?.height =
                if (interpolatedTime == 1.0f) ViewGroup.LayoutParams.WRAP_CONTENT else initHeight + (distanceHeight.toFloat() * interpolatedTime).toInt()
            this@runExpandAnimation?.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    animation.duration = duration ?: 300L
    this?.startAnimation(animation)
    return animation
}

/**
 * Collapse View Height
 * How to Use: AnimationUtil.collapse(txtLyric, 0, 300f), true, null)
 */
fun View?.runCollapseAnimation(
    targetHeight: Int,
    goneAfterDone: Boolean,
    duration: Long?
): Animation {
    val initialHeight = this?.measuredHeight ?: 0
    val distanceHeight = targetHeight - (this?.measuredHeight ?: 0)
    val animation: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            Intrinsics.checkParameterIsNotNull(t, "t")
            if (interpolatedTime == 1.0f && goneAfterDone) {
                this@runCollapseAnimation?.visibility = View.GONE
            }
            this@runCollapseAnimation?.layoutParams?.height =
                initialHeight + (distanceHeight.toFloat() * interpolatedTime).toInt()
            this@runCollapseAnimation?.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    animation.duration = duration ?: 300L
    this?.startAnimation(animation)
    return animation
}

/**
 * Chạy Anim File trong View
 * @param view
 * @param animRes
 * @return
 * Example: AnimationUtil.runXML(imageView, R.anim.fade_in)
 */
fun View?.runXMLAnimation(@AnimRes animRes: Int): Animation {
    val anim = AnimationUtils.loadAnimation(this?.context, animRes)
    this?.startAnimation(anim)
    return anim
}
//#endregion