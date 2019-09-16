package com.example.democommon.extension

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

/**
 * Check Valid TextView
 * Example:
 * if (!editText_username.validate(getString(R.string.error_not_format_email)) { it.isEmailValid() }) {
return false
}
 */
fun TextInputLayout.validate(errorMessage: String, validator: (String) -> Boolean): Boolean {
    this.editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            this@validate.error = if (validator(s.toString())) null else errorMessage
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

    this.error = if (validator(this.editText?.text.toString())) null else errorMessage

    return this.error == null
}

/**
 * SetText base editText Inside
 */
var TextInputLayout.text: String
    get() {
        return this.editText?.text.toString()
    }
    set(value) {
        this.editText?.text = SpannableStringBuilder(value)
    }