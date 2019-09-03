package com.nghiatl.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class IntentUtil {

    /**
     * Mở file
     * Using:
     * val uri = Uri.parse("www.w3schools.com/w3css/img_lights.jpg")
     * val i = IntentUtil.createOpenFileIntent(this, uri)
     * startActivity(i)
     * @param context
     * @param fileUri
     * @return
     */
    public static Intent createOpenFileIntent(Context context, Uri fileUri) {
        String mime = context.getContentResolver().getType(fileUri);

        // Open file with user selected app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    /**
     * Go to Setting Permission
     * Using:
     * val i = IntentUtil.createOpenAppSettingIntent(this)
     * startActivity(i)
     * @param context getApplicationContext()
     */
    public static Intent createOpenAppSettingIntent(Context context){
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return i;
    }
}
