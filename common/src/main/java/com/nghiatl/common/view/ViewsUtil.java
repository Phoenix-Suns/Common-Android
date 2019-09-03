package com.nghiatl.common.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.nghiatl.common.R;

/**
 * Created by Nghia on 2/19/2018.
 */
public class ViewsUtil {
    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * Check Position Inside View
     */
    public static boolean isInsideView(View containView, Float x, Float y) {
        return containView.getLeft() + containView.getTranslationX() <= x
                && x <= containView.getRight() + containView.getTranslationX()
                && containView.getTop() + containView.getTranslationY() <= y
                && y <= containView.getBottom() + containView.getTranslationY();
    }

    public static void setTextSize(TextView textView, Context context, @DimenRes int dimenResId) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) context.getResources().getDimensionPixelSize(dimenResId));
    }

    public static void setTint(ImageView imageView, Context context, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android
            imageView.setColorFilter(context.getColor(colorRes), PorterDuff.Mode.MULTIPLY);
        } else {
            // https://stackoverflow.com/questions/39437254/how-to-use-setimagetintlist-on-android-api-21
            ColorStateList csl = AppCompatResources.getColorStateList(context, colorRes);
            ImageViewCompat.setImageTintList(imageView, csl);
        }
    }
}
