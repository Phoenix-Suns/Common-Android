package com.nghiatl.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.widget.EditText
import android.os.Bundle
import com.nghiatl.common.R
import androidx.fragment.app.DialogFragment

class EditTextDialogFragment : DialogFragment() {

    var listener: ((value: String)->Unit)? = null
    private var editText: EditText? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // khởi tạo Fragment View Dialog
        // find Views
        val view = layoutInflater.inflate(R.layout.fragment_edit_text_dialog, null)
        editText = view.findViewById(R.id.editText)
        editText?.setText(arguments?.getString("value", ""))

        // Khởi tạo giao diện Dialog
        return AlertDialog.Builder(activity)
            .setTitle(arguments?.getString("title", "Enter Text"))
            .setView(view)
            .setPositiveButton("OK") { dialog, which -> // send success
                listener?.invoke(editText?.text.toString())
            }
            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }.create()
    }

    companion object {
        //--- Constructors ---
        // khởi tạo Fragment với Title
        fun newInstance(title: String?, value: String?): EditTextDialogFragment {
            // khởi tạo Listener truyền dữ liệu
            val frag = EditTextDialogFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("value", value)
            frag.arguments = bundle
            return frag
        }
    }
}