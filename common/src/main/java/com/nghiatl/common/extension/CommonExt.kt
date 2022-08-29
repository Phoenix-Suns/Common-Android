package com.nghiatl.common.extension

import android.app.Activity
import android.content.res.Resources
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


fun Double?.formatMoney(): String =
    this?.let {
        try {
            val format = DecimalFormat("###,###,###.###")
            return@let format.format(this)
        } catch (ex: Exception) {
        }
        "0"
    } ?: kotlin.run {
        "0"
    }

fun Long?.formatMoney(): String =
    this?.let {
        try {
            val format = DecimalFormat("###,###,###.###")
            return@let format.format(this)
        } catch (ex: Exception) {
        }
        "0"
    } ?: kotlin.run {
        "0"
    }

fun Calendar.formatDateTime(formatPattern: String, locale: Locale? = null): String {
    return try {
        val sdf = SimpleDateFormat(formatPattern, locale ?: Locale.getDefault())
        sdf.format(this.time)
    } catch (ex: Exception) {
        ""
    }
}

/**
 * Millis to Datetime String
 */
fun Long.formatDateTime(formatPattern: String, locale: Locale? = null): String {
    val cal = Calendar.getInstance().apply {
        timeInMillis = this@formatDateTime
    }
    return try {
        val sdf = SimpleDateFormat(formatPattern, locale ?: Locale.getDefault())
        sdf.format(cal.time)
    } catch (ex: Exception) {
        ""
    }
}

/**
 * convert Millisecond to hh:mm:ss
 */
fun Long.formatMinuteSecond(): String {
    val seconds = this / 1000 % 60
    val minute = this / (1000 * 60) // Max 60 minute Per Hour
    return String.format("%02d:%02d", minute, seconds)
}

/**
 * Convert Millisecond to Hour:minutes
 */
fun Long.formatHourMinute(): String {
    val minute = this / (1000 * 60) % 60 // Max 60 minute Per Hour
    val hour = this / (1000 * 60 * 60)
    return String.format("%02d:%02d", hour, minute)
}

/**
 * Convert Millisecond to minutes
 */
fun Long.formatMinute(): Long {
    return this / (1000 * 60)
}

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

const val DATE_TIME_FORMAT_dd_MMMM = "dd MMMM"
const val DATE_TIME_FORMAT_dd_MMMM_yyyy = "dd MMMM yyyy"

/**
 * millis to 5 second, minute ago
 */
fun Long.formatTimeAgo(
    justNowStr: String,
    daysAgoStr: String,
    yesterdayStr: String,
    hourAgoStr: String,
    hoursAgoStr: String,
    minutesAgoStr: String,
): String? {
    val millis = this
    val now = System.currentTimeMillis()
    if (millis > now || millis <= 0) {
        //return null;
        return justNowStr // fix: time server on future
    }
    val diff = now - millis

    /*
        if (diff < MINUTE_MILLIS) {
            return getString(R.string.date_utils_now);
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " " + getString(R.string.date_utils_minutes_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " " + getString(R.string.date_utils_hours_ago);
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " " + getString(R.string.date_utils_days_ago);
        } else if (getDiffYears(millis, now) < 1) {
            return formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM);
        } else {
            return formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM_yyyy);
        }
        */

    //#region Base IOS
    val oldCal = Calendar.getInstance()
    oldCal.timeInMillis = millis
    val totalMinutes: Long = diff / MINUTE_MILLIS
    val upToInDays = totalMinutes / (24 * 60)
    val upToInHours = totalMinutes / 60 - upToInDays * 24
    val upToInMinutes = totalMinutes - upToInDays * 24 - upToInHours * 60
    return if (diff < MINUTE_MILLIS) {
        justNowStr
    } else if (upToInDays > 0) {
        if (upToInDays < 7) {
            val daysInCalendar: Int = daysInCalendarPassedFrom(oldCal)
            if (daysInCalendar > 1) {
                "$daysInCalendar $daysAgoStr"
            } else {
                yesterdayStr
            }
        } else if (upToInDays < 365) {
            formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM)
        } else {
            formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM_yyyy)
        }
    } else if (upToInHours > 0) {
        // Hours
        upToInHours.toString() + " " + if (upToInHours == 1L) hourAgoStr else hoursAgoStr
    } else if (upToInMinutes > 1) {
        // Minus
        "$upToInMinutes $minutesAgoStr"
    } else if (getDiffYears(millis, now) < 1) {
        formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM)
    } else {
        formatTimestamp(millis, DATE_TIME_FORMAT_dd_MMMM_yyyy)
    }
    //#endregion
}

private fun daysInCalendarPassedFrom(fromDate: Calendar): Int {
    return try {
        val cal = Calendar.getInstance()
        val day = cal[Calendar.DAY_OF_YEAR]
        val fromDay = fromDate[Calendar.DAY_OF_YEAR]
        if (day > fromDay) day - fromDay else day + 365 - fromDay
    } catch (ex: java.lang.Exception) {
        1
    }
}

private fun formatTimestamp(timestamp: Long, format: String): String? {
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(Date(timestamp))
}

private fun getDiffYears(first: Long, last: Long): Int {
    val calFirst: Calendar = getCalendar(first)
    val calLast: Calendar = getCalendar(last)
    var diff = calLast[Calendar.YEAR] - calFirst[Calendar.YEAR]
    if (calFirst[Calendar.MONTH] > calLast[Calendar.MONTH] ||
        calFirst[Calendar.MONTH] == calLast[Calendar.MONTH] && calFirst[Calendar.DATE] > calLast[Calendar.DATE]
    ) {
        diff--
    }
    return diff
}

private fun getCalendar(timestamp: Long): Calendar {
    val cal = Calendar.getInstance()
    cal.time = Date(timestamp)
    return cal
}


/*inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, object : TypeToken<T>() {}.type)
}*/

fun Calendar.toFirstHourOfDay(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.toLastHourOfDay(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 23)
    this.set(Calendar.MINUTE, 59)
    this.set(Calendar.SECOND, 59)
    this.set(Calendar.MILLISECOND, 0)
    return this
}

fun String?.isNotEmptyOrBlank(): Boolean {
    if (this == null)
        return false

    return this.trim().isNotEmpty()
}
