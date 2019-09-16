package com.nghiatl.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.nghiatl.common.R
import com.nghiatl.common.view.RotateLoadingView

class ProgressBarHandler(context: Context) {
    private var mDialog: AlertDialog
    private var loadingView: RotateLoadingView

    init {
        val dialogBuilder = AlertDialog.Builder(context, R.style.DefaultProgressBarStyle)
        val inflater = LayoutInflater.from(context)
        val nullParent: ViewGroup? = null
        val dialogView = inflater.inflate(R.layout.layout_progress_loading, nullParent, false)
        loadingView = dialogView.findViewById(R.id.loadingView)
        dialogBuilder.setView(dialogView)
        mDialog = dialogBuilder.create()
        mDialog.setCancelable(false)
    }

    fun showProgress() {
        if (!mDialog.isShowing) {
            loadingView.start()
            mDialog.show()
        }
    }

    fun hideProgress() {
        if (mDialog.isShowing) {
            loadingView.stop()
            mDialog.dismiss()
        }
    }
}