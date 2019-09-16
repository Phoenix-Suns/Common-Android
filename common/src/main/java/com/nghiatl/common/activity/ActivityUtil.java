package com.nghiatl.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * Created by Nghia-PC on 7/9/2015.
 */
public class ActivityUtil {

    /**
     * Finish Activity after few second
     * @param delaySecond
     * @param currentActivity
     * @param toActivity
     */
    public static void delayFinishActivity(float delaySecond, final Activity currentActivity, final Class<?> toActivity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(currentActivity.getBaseContext(), toActivity);
                currentActivity.startActivity(mainIntent);
                currentActivity.finish();
            }
        }, (long)(delaySecond * 1000));
    }

    /**
     * Đặt kết quả trả về Activity
     * @param activity
     * @param data
     */
    public static void setResultOk(Activity activity, Intent data) {
        if (activity.getParent() == null) {
            activity.setResult(Activity.RESULT_OK, data);
        } else {
            activity.getParent().setResult(Activity.RESULT_OK, data);
        }
    }

    /**
     * Cài đặt Toolbar trong Activity
     * @param appCompatActivity Activity
     * @param toolbarId id của Toolbar
     * @param displayHomeAsUp
     * @param homeAsUpIndicatorId id ảnh của nút Home
     * @param displayIcon
     * @param iconId id ảnh của Icon
     */
    public static void setupToolbar(AppCompatActivity appCompatActivity, int toolbarId,
                                    boolean showTitle,
                                    boolean displayHomeAsUp, @Nullable Integer homeAsUpIndicatorId,
                                    boolean displayIcon, @Nullable Integer iconId){
        // lấy Toolbar
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(toolbarId);
        appCompatActivity.setSupportActionBar(toolbar);

        setupToolbar(appCompatActivity, showTitle, displayHomeAsUp, homeAsUpIndicatorId, displayIcon, iconId);
    }

    /**
     * Setup Toolbar for Fragment
     * @param appCompatActivity
     * @param showTitle
     * @param displayHomeAsUp
     * @param homeAsUpIndicatorId
     * @param displayIcon
     * @param iconId
     */
    public static void setupToolbar(AppCompatActivity appCompatActivity, boolean showTitle, boolean displayHomeAsUp, @Nullable Integer homeAsUpIndicatorId, boolean displayIcon, @Nullable Integer iconId) {
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            // Show Title
            if (showTitle) actionBar.setDisplayShowTitleEnabled(true);
            else actionBar.setDisplayShowTitleEnabled(false);

            // hiện Button Home hay Up
            actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);  // hiển thị Home/ Up

            if (homeAsUpIndicatorId != null)
                actionBar.setHomeAsUpIndicator(homeAsUpIndicatorId);  // đổi ảnh Home, Up

            // Hiện icon
            actionBar.setHomeButtonEnabled(displayIcon);

            if (iconId != null)
                actionBar.setIcon(ContextCompat.getDrawable(appCompatActivity, iconId));
        }
    }
}
