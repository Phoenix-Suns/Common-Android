package com.nghiatl.common

import android.content.*
import android.net.Uri
import androidx.fragment.app.Fragment


object ShareUtil {
    const val APP_GMAIL = "android.gm"
    const val APP_ZALO = "zalo"
    const val APP_SKYPE = "skype"

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }


    fun shareFacebook(fragment: Fragment, shareURL: String) {
        // https://developers.facebook.com/docs/sharing/android/
        //import com.facebook.share.model.ShareLinkContent
        //import com.facebook.share.widget.ShareDialog

        /*val shareContent = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(shareURL))
                .build()
        val shareDialog = ShareDialog(fragment)
        shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC)*/
    }


    fun shareToApp(appName: String, context: Context, subject: String, text: String, uri: Uri?) {
        val shareIntent = makeIntent(subject, text, uri)

        val activityList = context.packageManager.queryIntentActivities(shareIntent, 0)
        for (app in activityList) {
            if (app.activityInfo.name.contains(appName)) {

                val activity = app.activityInfo
                val name = ComponentName(activity.applicationInfo.packageName, activity.name)
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                shareIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                shareIntent.component = name

                context.startActivity(shareIntent)
                return
            }
        }

        //context.showToast("Không tìm thấy ứng dụng trên thiết bị của bạn" )
    }


    fun showDialogShareText(
        context: Context,
        dialogTitle: String,
        subject: String,
        text: String,
        uri: Uri
    ) {
        val sharingIntent = makeIntent(subject, text, uri)
        context.startActivity(Intent.createChooser(sharingIntent, dialogTitle))
    }

    fun shareText(context: Context, text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        context.startActivity(sendIntent)
    }


    fun shareTextAndImage(
        context: Context,
        title: String,
        shareLink: String,
        imgUri: Uri
    ) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareLink)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri)
        shareIntent.type = "image/*"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun shareImage(
        context: Context,
        title: String,
        imgUri: Uri
    ) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri)
        shareIntent.type = "image/*"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun shareUrl(
        context: Context?,
        url: String
    ) {
        var newUrl = if (!url.startsWith("http://") && !url.startsWith("https://"))
            "http://$url"
        else
            url

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(newUrl)
        context?.startActivity(intent)
    }

    private fun makeIntent(subject: String, text: String, uri: Uri?): Intent {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        if (uri != null)
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)

        return sharingIntent
    }
}