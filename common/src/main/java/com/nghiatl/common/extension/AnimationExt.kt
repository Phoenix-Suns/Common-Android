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
    duration: Long = DEFAULT_DURATION,
    listener: Transition.TransitionListener? = null
) {
    val transition = AutoTransition()
    transition.duration = duration
    transition.ordering = TransitionSet.ORDERING_TOGETHER
    if (listener != null) transition.addListener(listener)
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairFadeAnimation(duration: Long = DEFAULT_DURATION) {
    val transition = Fade()
    transition.duration = duration
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairChangeBoundsAnimation(duration: Long = DEFAULT_DURATION) {
    val transition = ChangeBounds()
    transition.duration = duration
    TransitionManager.beginDelayedTransition(this, transition)
}

fun ViewGroup?.repairSlideAnimation(slideUp: Boolean, duration: Long = DEFAULT_DURATION) {
    val transition = Slide(Gravity.BOTTOM)
    transition.duration = duration
    TransitionManager.beginDelayedTransition(this, transition)
}

fun View?.runChangeColorAnimation(
    duration: Long = DEFAULT_DURATION,
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
        .setDuration(duration)
        .start()
}

fun View?.runScaleAnimation(duration: Long = DEFAULT_DURATION): ScaleAnimation? {
    val animation = ScaleAnimation(
        1.15f, 1f, 1.15f, 1f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    this?.animation = animation
    animation.duration = duration
    animation.start()
    return animation
}

/**
 * Expand View Height
 */
fun View.runExpandAnimation(duration: Long = DEFAULT_DURATION): Animation {
    // Avoid Flash
    this@runExpandAnimation.alpha = 0f
    this@runExpandAnimation.visibility = View.GONE

    val initHeight = this.height
    this.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = this.measuredHeight
    val distanceHeight = targetHeight - initHeight
    val animation: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            if (interpolatedTime == 1.0f) {
                this@runExpandAnimation.alpha = 1f
                this@runExpandAnimation.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                this@runExpandAnimation.alpha = interpolatedTime
                this@runExpandAnimation.layoutParams.height = initHeight + (distanceHeight.toFloat() * interpolatedTime).toInt()
            }
            this@runExpandAnimation.requestLayout()
            this@runExpandAnimation.visibility = View.VISIBLE // Avoid Flash
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    animation.duration = duration
    this.startAnimation(animation)
    return animation
}

/**
 * Collapse View Height
 * How to Use: AnimationUtil.collapse(txtLyric, 0, 300f), true, null)
 */
fun View?.runCollapseAnimation(
    targetHeight: Int,
    goneAfterDone: Boolean,
    duration: Long = DEFAULT_DURATION
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
    animation.duration = duration
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