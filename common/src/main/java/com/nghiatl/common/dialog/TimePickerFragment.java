package com.nghiatl.common.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Nghia-PC on 8/19/2015.
 * Dialog chọn Giờ
 * val dlg = TimePickerFragment.newInstance(Calendar.getInstance())
 * dlg.setStyle(DatePickerDialog.THEME_TRADITIONAL)
 * dlg.setListener { view, hour, minute ->
 * 	    Log.e("nghia", "$hour - $minute")
 * }
 *
 * dlg.show(supportFragmentManager, "nghia")
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_HOUR_OF_DAY = "hour_of_day";
    private static final String ARG_MINUTE = "minute";
    IListener mListener;
    private int mStyle = android.R.style.Theme_DeviceDefault_Dialog_Alert;

    // khởi tạo dữ liệu ban đầu
    public static TimePickerFragment newInstance(int hourOfDay, int minute){
        TimePickerFragment fragment = new TimePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_HOUR_OF_DAY, hourOfDay);
        bundle.putInt(ARG_MINUTE, minute);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static TimePickerFragment newInstance(Calendar calendar) {
        Bundle args = new Bundle();
        args.putInt(ARG_HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        args.putInt(ARG_MINUTE, calendar.get(Calendar.MINUTE));

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // khởi tạo IListener
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
        int hour = bundle.getInt(ARG_HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        int minute = bundle.getInt(ARG_MINUTE, calendar.get(Calendar.MINUTE));

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                // Làm gì đó khi chọn xong thời gian
                // trả về listener
                mListener.onTimeSet(timePicker, hourOfDay, minute);
            }
        };

        // Tạo khởi tạo TimePickerDialog trả về
        TimePickerDialog timeDialog = new TimePickerDialog(
                getActivity(),
                mStyle,
                listener,
                hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        return timeDialog;
    }

    public interface IListener {
        /**
         * Đặt thời gian
         * @param timePicker
         * @param hourOfDay
         * @param minute
         */
        void onTimeSet(TimePicker timePicker, int hourOfDay, int minute);
    }
}
