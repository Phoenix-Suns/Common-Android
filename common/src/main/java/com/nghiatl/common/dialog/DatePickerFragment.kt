package com.nghiatl.common.dialog

import androidx.annotation.StyleRes
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * Created by Nghia-PC on 8/19/2015.
 * Dialog chọn ngày
 * @Example:
 * val dlg = DatePickerFragment.newInstance(Calendar.getInstance())
 * dlg.setStyle(DatePickerDialog.THEME_TRADITIONAL)
 * dlg.setListener { view, year, monthOfYear, dayOfMonth ->
 * Log.e("nghia", "$dayOfMonth - $monthOfYear - $year")
 * }
 * dlg.show(supportFragmentManager, "nghia")
 */
class DatePickerFragment : DialogFragment() {
    var listener: ((view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int)->Unit)? = null

    /** Set dialog Style
     * @param style
     * @Example
     * android.R.style.Theme_Material_Dialog_Alert
     * android.R.style.Theme_Material_Light_Dialog_Alert
     * DatePickerDialog.THEME_TRADITIONAL // show one line, click to change
     * DatePickerDialog.THEME_HOLO_DARK //show one line, slide to change
     */
    @StyleRes var style = DatePickerDialog.THEME_TRADITIONAL


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // lấy giá trị mặc định truyền vào,
        // không có => giờ mặc định là hiện tại
        val calendar = Calendar.getInstance()
        val bundle = this.arguments

        val year = bundle?.getInt(ARG_YEAR) ?: calendar[Calendar.YEAR]
        val monthOfYear = bundle?.getInt(ARG_MONTH_OF_YEAR) ?: calendar[Calendar.MONTH]
        val dayOfMonth = bundle?.getInt(ARG_DAY_OF_MONTH) ?: calendar[Calendar.DAY_OF_MONTH]

        //--- Tạo DatePicker lấy giá trị trả về
        val listener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Trả về Listener khi chọn xong
            listener?.invoke(view, year, monthOfYear, dayOfMonth)
        }
        return DatePickerDialog(
            requireContext(), style, listener, year, monthOfYear, dayOfMonth
        )
    }

    companion object {
        private const val ARG_YEAR = "ARG_YEAR"
        private const val ARG_MONTH_OF_YEAR = "ARG_MONTH_OF_YEAR"
        private const val ARG_DAY_OF_MONTH = "ARG_DAY_OF_MONTH"

        /** Khởi tạo Dialog  */
        fun newInstance(year: Int, monthOfYear: Int, dayOfMonth: Int): DatePickerFragment {
            val args = Bundle()
            args.putInt(ARG_YEAR, year)
            args.putInt(ARG_MONTH_OF_YEAR, monthOfYear)
            args.putInt(ARG_DAY_OF_MONTH, dayOfMonth)
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }

        /** Khởi tạo Dialog  */
        fun newInstance(calendar: Calendar): DatePickerFragment {
            val args = Bundle()
            args.putInt(ARG_YEAR, calendar[Calendar.YEAR])
            args.putInt(ARG_MONTH_OF_YEAR, calendar[Calendar.MONTH])
            args.putInt(ARG_DAY_OF_MONTH, calendar[Calendar.DAY_OF_MONTH])
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}