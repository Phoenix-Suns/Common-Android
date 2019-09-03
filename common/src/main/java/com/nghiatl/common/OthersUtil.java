package com.nghiatl.common;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.nghiatl.common.file.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OthersUtil {

    public static void shareText(Context context, String title, String subject, String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, title));
    }

    /**
     * Tải file thông qua Downloader
     * todo làm 1 service download file, mở file
     */
    public static void DownloadFile(Context context, String strUrl, String dirType, String subPath, boolean notifyComplete) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(strUrl));

        if (dirType != null) {
            // This put the download in the same Download dir the browser uses
            r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");
        }

        // When downloading music and videos they will be listed in the player
        // (Seems to be available since Honeycomb only)
        //r.allowScanningByMediaScanner();

        if (notifyComplete) {
            // Notify user when download is completed
            // (Seems to be available since Honeycomb only)
            r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        // Start download
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * mở Google Map theo tọa độ
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static Uri openMap(Context context, String latitude, String longitude){
        String strUrl = String.format("geo:%s,%s?z=%s", latitude, longitude, 16);
        Uri gmmIntentUri = Uri.parse(strUrl);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //mapIntent.setPackage("com.google.android.apps.maps");  // start Google map nếu có
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }

        return gmmIntentUri;
    }
}
