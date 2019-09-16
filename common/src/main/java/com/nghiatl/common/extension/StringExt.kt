package com.nghiatl.common.extension

import android.util.Patterns
import com.nghiatl.common.validate.ValidateUtil

fun String.isNameValid(): Boolean
        = this.isNotEmpty()


fun String.isEmailValid(): Boolean
        = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun String.isPhoneValid(): Boolean
        = Patterns.PHONE.matcher(this).matches() && ValidateUtil.isPhoneValid(this)


fun String.isPasswordValid(): Boolean
        = this.isNotEmpty() && this.length >= 6


fun String.isRePasswordValid(password: String): Boolean
        = this.isNotEmpty() && this == password

/**
 * Make Json more beautiful, Add /n /t to Json
 */
fun String.toPrettyJSONString(): String {

    val json = StringBuilder()
    var indentString = ""

    for (element in this) {
        when (element) {
            '{', '[' -> {
                json.append("\n" + indentString + element + "\n")
                indentString += "\t"
                json.append(indentString)
            }
            '}', ']' -> {
                indentString = indentString.replaceFirst("\t".toRegex(), "")
                json.append("\n" + indentString + element)
            }
            ',' -> json.append(element + "\n" + indentString)

            else -> json.append(element)
        }
    }

    return json.toString()
}