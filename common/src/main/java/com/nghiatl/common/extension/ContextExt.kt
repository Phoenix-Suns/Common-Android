package com.nghiatl.common.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String?) {
    Toast.makeText(this, message ?: "Error!", Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String?) {
    Toast.makeText(this, message ?: "Error!", Toast.LENGTH_LONG).show()
}