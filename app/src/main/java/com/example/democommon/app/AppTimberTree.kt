package com.example.democommon.app

import android.util.Log
import timber.log.Timber

class AppTimberDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        // Display on Logcat: com.example.democommon.ui.user.login.LoginActivity.loginClick(LoginActivity.kt:77): nghia
        // return element.toString()

        // Display on Logcat: .loginClick(LoginActivity.kt:77):
        return ".${element.methodName}(${element.fileName}:${element.lineNumber})"
    }
}

class AppTimberReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }

        /*Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

        if (t == null) {
            Crashlytics.logException(new Exception(message))
        } else {
            Crashlytics.logException(t)
        }*/
    }
}