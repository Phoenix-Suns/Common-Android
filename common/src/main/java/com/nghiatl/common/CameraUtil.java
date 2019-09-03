package com.nghiatl.common;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Nghia-PC on 8/27/2015.
 * Hàm hỗ trợ Camera
 */
public class CameraUtil {
    /**
     * Kiểm tra tồn tại Camera
     * @return
     */
    public boolean isSupportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
