package com.nghiatl.common.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Nghia-PC on 8/19/2015.
 * Dialog chọn ngày
 * @Example:
val dlg = DatePickerFragment.newInstance(Calendar.getInstance())
dlg.setStyle(DatePickerDialog.THEME_TRADITIONAL)
dlg.setListener { view, year, monthOfYear, dayOfMonth ->
    Log.e("nghia", "$dayOfMonth - $monthOfYear - $year")
}
dlg.show(supportFragmentManager, "nghia")
 */
public class DatePickerFragment extends DialogFragment {

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH_OF_YEAR = "month";
    private static final String ARG_DAY_OF_MONTH = "day";
    private IListener mListener;
    private int mStyle = android.R.style.Theme_DeviceDefault_Dialog_Alert;

    /** Khởi tạo Dialog */
    public static DatePickerFragment newInstance(
            @Nullable int year, @Nullable int monthOfYear, @Nullable int dayOfMonth) {

        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH_OF_YEAR, monthOfYear);
        args.putInt(ARG_DAY_OF_MONTH, dayOfMonth);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /** Khởi tạo Dialog */
    public static DatePickerFragment newInstance(Calendar calendar) {

        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, calendar.get(Calendar.YEAR));
        args.putInt(ARG_MONTH_OF_YEAR, calendar.get(Calendar.MONTH));
        args.putInt(ARG_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /** Set sự kiện */
    public void setListener(IListener listener){
        this.mListener = listener;
    }

    /** Set dialog Style
     * @param style
     * @Example
     * android.R.style.Theme_Material_Dialog_Alert
     * android.R.style.Theme_Material_Light_Dialog_Alert
     * DatePickerDialog.THEME_TRADITIONAL // show one line, click to change
     * DatePickerDialog.THEME_HOLO_DARK //show one line, slide to change
     * */
    public void setStyle(@StyleRes int style) { this.mStyle = style; }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // lấy giá trị mặc định truyền vào,
        // không có => giờ mặc định là hiện tại
        final Calendar calendar = Calendar.getInstance();

        Bundle bundle = this.getArguments();
        int year = bundle.getInt(ARG_YEAR, calendar.get(Calendar.YEAR));
        int monthOfYear = bundle.getInt(ARG_MONTH_OF_YEAR, calendar.get(Calendar.MONTH));
        int dayOfMonth = bundle.getInt(ARG_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        //--- Tạo DatePicker lấy giá trị trả về
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Trả về Listener khi chọn xong
                mListener.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), mStyle, listener, year, monthOfYear, dayOfMonth );

        return datePickerDialog;
    }

    /** Interface trung gian */
    public interface IListener {
        /** Đặt thời gian */
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}
