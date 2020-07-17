package com.nghiatl.common.application

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Process
import kotlin.system.exitProcess

object ApplicationUtil {
    public fun restartApplication(context: Context) {
        val i = context.packageManager.getLaunchIntentForPackage(context.packageName)
        i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val mPendingIntent = PendingIntent.getActivity(context, 123, i, PendingIntent.FLAG_CANCEL_CURRENT)
        val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)

        Process.killProcess(Process.myPid())
        exitProcess(0)
    }
}