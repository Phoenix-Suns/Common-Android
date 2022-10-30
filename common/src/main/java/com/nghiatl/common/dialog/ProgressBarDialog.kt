package com.nghiatl.common.dialog

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.nghiatl.common.R
import com.nghiatl.common.view.RotateLoadingView

class ProgressBarDialog(context: Context) {
    private var dialog: AlertDialog
    private var loadingView: RotateLoadingView

    init {
        val dialogBuilder = AlertDialog.Builder(context, R.style.DefaultProgressBarStyle)
        val inflater = LayoutInflater.from(context)
        val nullParent: ViewGroup? = null
        val dialogView = inflater.inflate(R.layout.layout_progress_loading, nullParent, false)
        dialogBuilder.setView(dialogView)
        dialog = dialogBuilder.create()
        dialog.setCancelable(false)

        loadingView = dialogView.findViewById(R.id.loadingView)
    }

    fun showProgress(cancelable: Boolean) {
        if (!dialog.isShowing) {
            loadingView.start()

            dialog.setCancelable(cancelable)
            dialog.show()
        }
    }

    fun hideProgress() {
        if (dialog.isShowing) {
            loadingView.stop()
            dialog.dismiss()
        }
    }

    fun requestFullScreen() {
        dialog.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}