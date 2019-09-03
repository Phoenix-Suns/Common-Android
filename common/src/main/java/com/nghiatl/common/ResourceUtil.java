package com.nghiatl.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;

import java.io.InputStream;

/**
 * Created by Nghia-PC on 7/29/2015.
 * Xử lý Resource
 */
public class ResourceUtil {

    /**
     * Lấy ảnh từ Resource
     * @param context
     * @param defType
     * @param fileName
     * @return
     * Example: val bitmap = ResourceUtil.getBitmap(this, "drawable", "ic_love")
     */
    public static Bitmap getBitmap(Context context, String defType, String fileName) {
        int flagId = context.getResources().getIdentifier(fileName, defType, context.getPackageName());
        InputStream imageStream = context.getResources().openRawResource(flagId);
        return BitmapFactory.decodeStream(imageStream);
    }

}
