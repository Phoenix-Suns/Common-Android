package com.nghiatl.common.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.nghiatl.common.R

object LoadingDialog {

    private var loadingDialog : Dialog? = null

    fun show(context : Context) {
        dismiss()

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_waiting_dialog, null)
        loadingDialog = Dialog(context, R.style.Common_Dialog_Transparent)
        loadingDialog?.setCancelable(false)
        loadingDialog?.setCanceledOnTouchOutside(false)
        loadingDialog?.setContentView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        loadingDialog?.show()
    }

    fun dismiss() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.cancel()
            loadingDialog?.dismiss()
        }

        if (loadingDialog != null) {
            loadingDialog = null
        }
    }
}