package com.nghiatl.common

import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import java.text.SimpleDateFormat
import java.util.*

object IntentUtil {
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
    fun openFileIntent(context: Context, fileUri: Uri?): Intent {
        val mime = context.contentResolver.getType(fileUri!!)

        // Open file with user selected app
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, mime)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return intent
    }

    /**
     * Go to Setting Permission
     * Using:
     * val i = IntentUtil.createOpenAppSettingIntent(this)
     * startActivity(i)
     * @param context getApplicationContext()
     */
    fun openSystemSettingIntent(context: Context): Intent {
        val i = Intent()
        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:" + context.packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        return i
    }

    fun openCapturePhotoSaveInGalleryIntent(contentWrapper: ContextWrapper, storedPhotoUri: ((Uri?) -> Unit)?): Intent {
        val values = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(System.currentTimeMillis())
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            )
        }
        val uri = contentWrapper.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        storedPhotoUri?.invoke(uri)

        return intent
    }

    /**
     * @param type = "image/\*"
     **/
    fun openSystemPickFile(type: String): Intent {
        val intent = Intent()
        intent.type = type
        intent.action = Intent.ACTION_GET_CONTENT
        return Intent.createChooser(intent, "Select File")
    }
}