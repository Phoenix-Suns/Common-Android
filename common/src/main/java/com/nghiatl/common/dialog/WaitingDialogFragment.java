package com.nghiatl.common.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.nghiatl.common.R;

/**
 * Example:
 * val i = pickImageDlg.createPickImageIntent(this, "abc.jpg")
 * startActivityForResult(i, 100)
 */
public class WaitingDialogFragment extends DialogFragment {

    // --- Variables ---
    ProgressDialogFragment.IListener mListener;

    //--- Constructors ---
    // khởi tạo Fragment với Title
    public static WaitingDialogFragment newInstance(boolean allowCancel) {
        // khởi tạo Listener truyền dữ liệu
        WaitingDialogFragment frag = new WaitingDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("cancel", allowCancel);

        frag.setArguments(bundle);

        return frag;
    }

    public static void show(FragmentManager manager, String dlgTag, boolean allowCancel) {

        WaitingDialogFragment dialog = (WaitingDialogFragment) manager.findFragmentByTag(dlgTag);
        if (dialog == null) dialog = WaitingDialogFragment.newInstance(allowCancel);

        dialog.setCancelable(allowCancel);
        dialog.show(manager, dlgTag);
    }

    public static void dismiss(FragmentManager manager, String dlgTag) {
        WaitingDialogFragment dialog = (WaitingDialogFragment) manager.findFragmentByTag(dlgTag);
        if (dialog != null) dialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_waiting_dialog, null);

        // Khởi tạo giao diện Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.Common_Dialog_Transparent))
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


    // khởi tạo listener - do không cho tạo Constructor
    public void setListener(ProgressDialogFragment.IListener listener){
        mListener = listener;
    }


    /**
     * Interface trung gian
     */
    public interface IListener {
        void onCancelClick(DialogInterface dialog, int which);
    }
}
