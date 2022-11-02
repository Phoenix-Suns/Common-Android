package com.nghiatl.common.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.nghiatl.common.R


class LoadingDialogView(private val containerView: ViewGroup) {
    private var loadingView: View? = null

    fun show(cancelWhenClick: Boolean) {
        dismiss()
        val context = containerView.context

        //===== Add View =====
        loadingView = LayoutInflater.from(context).inflate(R.layout.fragment_waiting_dialog, null)
        loadingView?.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        containerView.addView(loadingView)

        // ===== Cancel =====
        if (cancelWhenClick) {
            loadingView?.setOnClickListener {
                dismiss()
            }
        }
    }

    fun dismiss() {
        containerView.removeView(loadingView)
    }
}