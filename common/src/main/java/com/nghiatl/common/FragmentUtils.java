package com.nghiatl.common;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Imark-N on 10/8/2015.
 */
public class FragmentUtils {

    /** Đổi Fragment */
    public static void replaceFragment(FragmentActivity fragmentActivity, @IdRes int containerViewId, Fragment fragment, @Nullable String tag) {
        FragmentManager fragManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragTran = fragManager.beginTransaction();
        fragTran.replace(containerViewId, fragment, tag);

        if (tag != null) {
            // quay trở lại Fragment
            fragTran.addToBackStack(tag);
        }

        fragTran.commit();
    }
}
