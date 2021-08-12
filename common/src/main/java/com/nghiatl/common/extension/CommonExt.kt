package com.nghiatl.common.extension

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

inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, object : TypeToken<T>() {}.type)
}

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
