package com.nghiatl.common.extension

import android.graphics.Color
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
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

/**
editText.setText("This is @mi a test @ngh strike")
var builder4 = editText.text.setSpans(arrayListOf(
    ForegroundColorSpan(Color.GREEN),
    BackgroundColorSpan(Color.BLUE)
), "@mi", "@minh")
editText.setText(builder4)
 */
fun CharSequence.setSpans(spans: List<Any>, removeText: String, replaceWithText: String): SpannableStringBuilder {
    var builder = SpannableStringBuilder(this)
    val start = builder.indexOf(removeText)
    val end = start + removeText.length

    val spannableString = SpannableString(replaceWithText)

    for (span in spans) {
        spannableString.setSpan(span, 0, replaceWithText.length, 0)
    }

    builder.replace(start, end, spannableString)
    return builder
}