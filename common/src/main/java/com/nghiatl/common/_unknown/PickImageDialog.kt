package com.nghiatl.common._unknown

import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.os.Parcelable
import java.io.File
import java.util.ArrayList

/**
 * Tạo Dialog chọn ảnh. <br></br>
 * https://gist.github.com/TiagoGouvea/4739629db5f55d18cd72 <br></br>
 * @Example
 * Using:
 * // Event Chọn Ảnh: {
 * val i = pickImageDlg.createPickImageIntent(this, "abc.jpg")
 * startActivityForResult(i, 100)
 * }
 * // Trả về:
 * override fun onActivityResult {
 * //...
 * val uriResult = pickImageDlg.getPickImageResultUri(data)
 * }
 */
class PickImageDialog(title: String?) {
    private var mImageResult = Uri.parse("")
    private var mTitle: String? = "Select source"

    init {
        mTitle = title
    }

    /**
     * Create a chooser intent to select the  source to get image from.<br></br>
     * The source can be camera's  (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br></br>
     * All possible sources are added to the  intent chooser.
     * @param: filename "pickImageResult.jpeg"
     */
    fun createPickImageIntent(context: Context, fileName: String?): Intent {

        // Determine Uri of camera image to  save.
        mImageResult = createCaptureImageOutputUri(context, fileName)
        val allIntents: MutableList<Intent> = ArrayList()
        val packageManager = context.packageManager

        // collect all camera intents
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (mImageResult != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageResult)
            }
            allIntents.add(intent)
        }

        // collect all gallery intents
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }

        // the main intent is the last in the  list (fucking android) so pickup the useless one
        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)

        // Create a chooser from the main  intent
        val chooserIntent = Intent.createChooser(mainIntent, mTitle)

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }

    /**
     * Get URI to image received from capture  by camera.
     * @param context
     * @param fileName "pickImageResult.jpeg"
     */
    fun createCaptureImageOutputUri(context: Context, fileName: String?): Uri? {
        var outputFileUri: Uri? = null
        val getImage = context.externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, fileName))
        }
        return outputFileUri
    }

    fun getPickImageResultUri(data: Intent?): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        return if (isCamera) mImageResult else data!!.data
    }
}