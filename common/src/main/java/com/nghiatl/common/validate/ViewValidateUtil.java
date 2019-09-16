package com.nghiatl.common.validate;

//import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by Nghia-PC on 8/25/2015.
 * Lớp kiểm tra View hợp lệ
 * Tự hiện lỗi
 */
public class ViewValidateUtil {


    //region TextView Validate
    /**
     * Kiểm tra Email, tự set lỗi
     * @param editText
     * @param errorMessage
     * @return true: đúng email
     */
    public static boolean isEmailAddress(TextView editText, String errorMessage) {
        return isRegexValid(editText, ValidateUtil.REGEX_EMAIL, errorMessage);
    }

    /**
     * Kiểm tra Phone, tự set lỗi
     * @param textView
     * @param errorMessage
     * @return true: đúng phone
     */
    public static boolean isPhoneNumber(TextView textView, String errorMessage) {
        //return isRegexValid(textView, ValidateUtil.REGEX_PHONE, errorMessage);
        textView.setError(null);  // clear error

        String text = textView.getText().toString().trim();

        // pattern doesn't match so returning false
        for (String regex: ValidateUtil.REGEX_PHONE) {
            if (!Pattern.matches(regex, text)) {
                textView.setError(errorMessage);
                return false;
            }
        }
        return true;
    }


    /**
     * Kiểu tra Regex, tự set lỗi
     * @param textView
     * @param regex
     * @param errorMessage
     * @return true: trùng regex
     */
    public static boolean isRegexValid(TextView textView, String regex, String errorMessage) {
        textView.setError(null);  // clear error

        String text = textView.getText().toString().trim();

        // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }

    /** kiểm tra có Text, tự set lỗi
     * True: nếu có Text*/
    public static boolean hasText(TextView textView, String errorMessage) {
        textView.setError(null);  // clear error

        String text = textView.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {
            // Không có text
            textView.setError(errorMessage);
            return false;
        }

        return true;
    }


    /**
     * Kiểm tra trùng
     * @param textView
     * @param textView2
     * @param errorMessage
     * @return true: nếu trùng
     */
    public static boolean isTextEquals(TextView textView, TextView textView2, String errorMessage) {
        // clear error
        textView.setError(null);
        textView2.setError(null);

        String text1 = textView.getText().toString().trim();
        String text2 = textView2.getText().toString().trim();

        if (!text1.equals(text2)) {
            // Không có text
            textView.setError(errorMessage);
            textView2.setError(errorMessage);

            return false;
        }

        return true;
    }
    public static boolean isPositiveNumber(TextView textView, String errorMessage) {
        return isRegexValid(textView, ValidateUtil.REGEX_POSITIVE_NUMBER, errorMessage);
    }

    //endregion
}
