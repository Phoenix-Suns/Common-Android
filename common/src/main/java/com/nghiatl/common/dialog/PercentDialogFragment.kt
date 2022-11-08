package com.nghiatl.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.widget.ProgressBar
import android.widget.TextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nghiatl.common.R
import android.content.DialogInterface
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Loading Progress dialog
 * Sample:
 * val dlg = ProgressDialogFragment.newInstance("Running Man", false)
 * dlg.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
 * dlg.isCancelable = false
 * dlg.show(supportFragmentManager, "nghia")
 */
class PercentDialogFragment : DialogFragment() {
    private val TAG by lazy { this::class.simpleName }

    // --- Variables ---
    var listener: IListener? = null
    private var mMessage: String? = null
    private var mMax = 100
    private var mProgress = 0
    private var mIndeterminate = false
    private var progressBar: ProgressBar? = null
    private var textViewMessage: TextView? = null
    private var textViewPercent: TextView? = null
    private var textViewTask: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // khởi tạo Fragment View Dialog
        // find Views
        val rootView = layoutInflater.inflate(R.layout.fragment_percent_dialog, null)
        findView(rootView)

        // --- Init Var ---
        // set Progress Style
        Log.e(TAG, "mIndeterminate $mIndeterminate , mMessage $mMessage , mProgress $mProgress ")
        setIndeterminate(mIndeterminate)
        setMessage(mMessage)
        setMax(mMax)
        setProgress(mProgress)


        // Khởi tạo giao diện Dialog
        val builder = AlertDialog.Builder(activity)
            .setTitle(arguments?.getString("title", ""))
            .setView(rootView)
        if (arguments?.getBoolean("cancel") == true)
            builder.setPositiveButton("Cancel") { dialog, which ->
                if (listener != null) listener!!.onCancelClick(dialog, which)
                dialog.dismiss()
        }
        return builder.create()
    }

    private fun findView(rootView: View) {
        textViewMessage = rootView.findViewById(R.id.textView_Message)
        textViewPercent = rootView.findViewById(R.id.textView_Percent)
        textViewTask = rootView.findViewById(R.id.textView_Task)
        progressBar = rootView.findViewById(R.id.progressBar)
    }

    fun setMessage(message: String?) {
        mMessage = message
        textViewMessage?.text = message
    }

    /**
     * Set max value
     * @param max
     */
    fun setMax(max: Int) {
        mMax = max
        progressBar?.max = mMax
        updateProgressVal()
    }

    /**
     * Set current value
     * @param progress
     */
    fun setProgress(progress: Int) {
        mProgress = progress
        progressBar?.progress = mProgress
        updateProgressVal()
    }

    /**
     * Update progress Information
     */
    private fun updateProgressVal() {
        textViewTask?.text = "$mProgress/$mMax"
        val percent = mMax * mProgress / 100
        textViewPercent?.text = "$percent%"
    }

    /**
     * Progress end now, or show Percent
     * @param indeterminate
     */
    fun setIndeterminate(indeterminate: Boolean) {
        mIndeterminate = indeterminate
        progressBar?.isIndeterminate = mIndeterminate
    }

    /**
     * Interface trung gian
     */
    interface IListener {
        fun onCancelClick(dialog: DialogInterface?, which: Int)
    }

    companion object {
        //--- Constructors ---
        // khởi tạo Fragment với Title
        fun newInstance(title: String?, allowCancel: Boolean): PercentDialogFragment {
            // khởi tạo Listener truyền dữ liệu
            val frag = PercentDialogFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putBoolean("cancel", allowCancel)
            frag.arguments = bundle
            return frag
        }

        fun show(
            manager: FragmentManager, dlgTag: String?, allowCancel: Boolean,
            title: String?, message: String?
        ): PercentDialogFragment? {
            var dialog = manager.findFragmentByTag(dlgTag) as PercentDialogFragment?
            if (dialog == null) dialog = newInstance(title, false)
            dialog.setMessage(message)

            // không cho hủy
            if (!allowCancel) {
                dialog.isCancelable = false // không cho hủy
            }
            dialog.show(manager, dlgTag)
            return dialog
        }

        fun dismiss(manager: FragmentManager, dialogTag: String?) {
            val dialog = manager.findFragmentByTag(dialogTag) as PercentDialogFragment?
            dialog?.dismiss()
        }
    }
}