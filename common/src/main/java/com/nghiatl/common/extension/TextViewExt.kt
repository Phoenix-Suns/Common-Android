package com.nghiatl.common.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Check Valid TextView
 * Example:
if (!editText_username.validate(getString(R.string.error_not_format_email)) { it.isEmailValid() }) {
    return false
}
 */
fun TextView.validate(errorMessage: String, validator: (String) -> Boolean) : Boolean {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            this@validate.error = if (validator(s.toString())) null else errorMessage
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
    this.error = if (validator(this.text.toString())) null else errorMessage

    return this.error == null
}


fun TextView.validateNormal(errorMessage: String, validator: (String) -> Boolean) : Boolean {
    this.error = if (validator(this.text.toString())) null else errorMessage
    return this.error == null
}