package com.nghiatl.common.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.nghiatl.common.R;

/**
 * Loading Progress dialog
 * Sample:
 * val dlg = ProgressDialogFragment.newInstance("Running Man", false)
 * dlg.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
 * dlg.isCancelable = false
 * dlg.show(supportFragmentManager, "nghia")
 */
public class ProgressDialogFragment extends DialogFragment {

    // --- Variables ---
    IListener mListener;
    String mMessage;
    int mMax = 100;
    int mProgress = 0;
    boolean mIndeterminate = true;

    private ProgressBar mProgressBar;
    private TextView mTextView_Message;
    private TextView mTextView_Percent;
    private TextView mTextView_Task;

    //--- Constructors ---
    // khởi tạo Fragment với Title
    public static ProgressDialogFragment newInstance(@Nullable String title, boolean allowCancel) {
        // khởi tạo Listener truyền dữ liệu
        ProgressDialogFragment frag = new ProgressDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putBoolean("cancel", allowCancel);

        frag.setArguments(bundle);

        return frag;
    }


    public static ProgressDialogFragment show(
            FragmentManager manager, String dlgTag, boolean allowCancel,
            @Nullable String title, @Nullable String message) {

        ProgressDialogFragment dialog = (ProgressDialogFragment) manager.findFragmentByTag(dlgTag);
        if (dialog == null) dialog = ProgressDialogFragment.newInstance(title, false);

        dialog.setMessage(message);

        // không cho hủy
        if (!allowCancel) {
            dialog.setCancelable(false);  // không cho hủy
        }
        dialog.show(manager, dlgTag);

        return dialog;
    }

    public static void dismiss(FragmentManager manager, String dialogTag) {
        ProgressDialogFragment dialog = (ProgressDialogFragment) manager.findFragmentByTag(dialogTag);
        if (dialog == null) dialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(R.style.AlertDialog_AppCompat, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // khởi tạo Fragment View Dialog
        // find Views
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_progress_dialog, null);
        findView(rootView);

        // --- Init Var ---
        // set Progress Style
        /*setIndeterminate(mIndeterminate);
        setMessage(mMessage);
        setMax(mMax);
        setProgress(mProgress);*/


        // Khởi tạo giao diện Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title", ""))
                .setView(rootView);
        if (getArguments().getBoolean("cancel", false))
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListener != null) mListener.onCancelClick(dialog, which);
                    dialog.dismiss();
                }
            });

        AlertDialog dialog = builder.create();

        return dialog;
    }

    private void findView(View rootView) {
        mTextView_Message = rootView.findViewById(R.id.textView_Message);
        mTextView_Percent = rootView.findViewById(R.id.textView_Percent);
        mTextView_Task = rootView.findViewById(R.id.textView_Task);
        mProgressBar = rootView.findViewById(R.id.progressBar);
    }

    // khởi tạo listener - do không cho tạo Constructor
    public void setListener(IListener listener){
        mListener = listener;
    }


    public void setMessage(String message) {
        this.mMessage = message;

        if (mTextView_Message != null)
        {
            mTextView_Message.setText(message);
        }
    }

    /**
     * Set max value
     * @param max
     */
    public void setMax(int max) {
        this.mMax = max;

        if (mProgressBar != null) mProgressBar.setMax(mMax);
        updateProgressVal();
    }

    /**
     * Set current value
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;

        if (mProgressBar != null) mProgressBar.setProgress(mProgress);
        updateProgressVal();
    }

    /**
     * Update progress Information
     */
    private void updateProgressVal() {
        if (mTextView_Task != null) {
            mTextView_Task.setText(mProgress+"/"+mMax);
        }

        int percent = mProgressBar.getMax() * mProgressBar.getProgress() / 100;
        if (mTextView_Percent != null) mTextView_Percent.setText(percent+"%");
    }

    /**
     * Progress end now, or show Percent
     * @param indeterminate
     */
    public void setIndeterminate(boolean indeterminate) {
        this.mIndeterminate = indeterminate;

        if (mProgressBar != null) {
            mProgressBar.setIndeterminate(mIndeterminate);
        }
    }

    /**
     * Interface trung gian
     */
    public interface IListener {
        void onCancelClick(DialogInterface dialog, int which);
    }
}
