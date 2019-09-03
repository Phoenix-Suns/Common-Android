package com.nghiatl.common.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nghiatl.common.R;
import com.nghiatl.common.dialog.ProgressDialogFragment;
import com.nghiatl.common.dialog.WaitingDialogFragment;

/**
 * Created by Nghia-PC on 8/27/2015.
 * Các Dialog hỗ trợ nhanh
 */
public class DialogUtil {
    /**
     * Hiện Dialog Mở GPS
     * */
    public static void showLocationSettingAlert(
            final Context context, String title, String message, String positiveButtonText, String negativeButtonText){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setTitle(title).setMessage(message);

        alertDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    /**
     * Mở Dialog hỏi mở Mobile Data
     */
    public static void showNetworkSettingAlert(
            final Context context, String title, String message, String positiveButtonText, String negativeButtonText){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setTitle(title).setMessage(message);

        alertDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Ẩn/Hiện Progress Đang hoạt động
     * Ko tắt khi ở ngoài, không cho hủy
     * Deprecate at 26 API
     * @param progressDialog
     * @param dismissDlg
     * @deprecated
     */
    @Deprecated
    public static void showProgressDialog(
            ProgressDialog progressDialog, boolean dismissDlg, boolean allowCancel, @Nullable String title, @Nullable String message) {

        if (dismissDlg) {

            if (title != null)
                progressDialog.setTitle(title);
            if (message != null)
                progressDialog.setMessage(message);

            // không cho hủy
            if (!allowCancel) {
                progressDialog.setCancelable(false);  // không cho hủy
                progressDialog.setCanceledOnTouchOutside(false);  // không ẩn khi click ngoài
            }

            progressDialog.show();

        } else {
            progressDialog.dismiss();
            progressDialog.cancel();  // giống dismiss, gọi hàm dialog.cancellistner
        }
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
