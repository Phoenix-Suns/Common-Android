package com.nghiatl.common.dialog

import com.nghiatl.common.R
import android.content.Intent
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

/**
 * Created by Nghia-PC on 8/27/2015.
 * Các Dialog hỗ trợ nhanh
 */
object DialogUtil {
    /**
     * Hiện Dialog Mở GPS
     */
    fun showLocationSettingAlert(
        context: Context,
        title: String?,
        message: String?,
        positiveButtonText: String?,
        negativeButtonText: String?
    ) {
        val alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
        alertDialog.setTitle(title).setMessage(message)
        alertDialog.setPositiveButton(positiveButtonText) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        alertDialog.setNegativeButton(negativeButtonText) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    /**
     * Mở Dialog hỏi mở Mobile Data
     */
    fun showNetworkSettingAlert(
        context: Context,
        title: String?,
        message: String?,
        positiveButtonText: String?,
        negativeButtonText: String?
    ) {
        val alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
        alertDialog.setTitle(title).setMessage(message)
        alertDialog.setPositiveButton(positiveButtonText) { dialog, which ->
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = Settings.ACTION_DATA_ROAMING_SETTINGS
            context.startActivity(intent)
        }
        alertDialog.setNegativeButton(negativeButtonText) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    /**
     * Ẩn/Hiện Progress Đang hoạt động
     * Ko tắt khi ở ngoài, không cho hủy
     * Deprecate at 26 API
     * @param progressDialog
     * @param showDialog
     */
    @Deprecated("Deprecated by android: ProgressDialog")
    fun showMessageProgressDialog(
        progressDialog: ProgressDialog,
        showDialog: Boolean,
        allowCancel: Boolean,
        title: String?,
        message: String?
    ) {
        if (showDialog) {
            if (title != null) progressDialog.setTitle(title)
            if (message != null) progressDialog.setMessage(message)

            // không cho hủy
            if (!allowCancel) {
                progressDialog.setCancelable(false) // không cho hủy
                progressDialog.setCanceledOnTouchOutside(false) // không ẩn khi click ngoài
            }
            progressDialog.show()
        } else {
            progressDialog.dismiss()
            progressDialog.cancel() // giống dismiss, gọi hàm dialog.cancellistner
        }
    }

    @Deprecated("Deprecated by android: ProgressDialog")
    fun showLoadingDialog(context: Context?, allowCancel: Boolean): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        if (!allowCancel) {
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
        }
        return progressDialog
    }
}