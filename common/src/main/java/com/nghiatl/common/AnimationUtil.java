package com.nghiatl.common;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

import androidx.annotation.AnimRes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

/**
 * @deprecated replace by AnimationExt
 */
public class AnimationUtil {

    public static final int DURATION = 300;

    /**
     * Auto Show/Hide Animation
     * @param viewGroup
     * @param listener
     */
    public static void runAutoChange(ViewGroup viewGroup, Transition.TransitionListener listener) {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(DURATION);
        transition.setOrdering(TransitionSet.ORDERING_TOGETHER);
        if (listener != null) transition.addListener(listener);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void runFade(ViewGroup viewGroup) {
        Fade transition = new Fade();
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void runChangeBounds(ViewGroup viewGroup) {
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void runSlide(ViewGroup viewGroup, boolean slideUp) {
        Slide transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void runChangeColor(View view) {
        int colorFrom = Color.RED;
        int colorTo = Color.GREEN;
        ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                .setDuration(DURATION)
                .start();
    }

    public static ScaleAnimation runScale(View view) {
        ScaleAnimation animation = new ScaleAnimation(
                1.15f, 1, 1.15f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        view.setAnimation(animation);
        animation.setDuration(100);
        animation.start();

        return animation;
    }

    /**
     * Expand View Height
     */
    public final Animation runExpand(@NotNull final View v, @Nullable Long duration) {
        final int initHeight = v.getHeight();

        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targetHeight = v.getMeasuredHeight();

        final int distanceHeight = targetHeight - initHeight;

        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, @NotNull Transformation t) {

                v.getLayoutParams().height =
                        interpolatedTime == 1.0F
                                ? ViewGroup.LayoutParams.WRAP_CONTENT
                                : initHeight + (int)((float)distanceHeight * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration != null ? duration : 300L);
        v.startAnimation(animation);

        return animation;
    }

    /**
     * Collapse View Height
     * How to Use: AnimationUtil.collapse(txtLyric, 0, 300f), true, null)
     */
    public final Animation runCollapse(@NotNull final View v, int targetHeight, final boolean goneAfterDone, @Nullable Long duration) {

        final int initialHeight = v.getMeasuredHeight();
        final int distanceHeight = targetHeight - v.getMeasuredHeight();

        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, @NotNull Transformation t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                if (interpolatedTime == 1.0F && goneAfterDone) {
                    v.setVisibility(View.GONE);
                }

                v.getLayoutParams().height = initialHeight + (int)((float)distanceHeight * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration != null ? duration : 300L);
        v.startAnimation(animation);

        return animation;
    }

    /**
     * Chạy Anim File trong View
     * @param view
     * @param animRes
     * @return
     * Example: AnimationUtil.runXML(imageView, R.anim.fade_in)
     */
    public static final Animation runXML(@NotNull View view, @AnimRes int animRes) {
        Animation anim = AnimationUtils.loadAnimation(view.getContext(), animRes);
        view.startAnimation(anim);
        return anim;
    }
}
