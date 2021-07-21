package com.nghiatl.common.file

import java.io.InputStream
import java.nio.charset.Charset

object TextUtil {
    fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
        return this.bufferedReader(charset).use { it.readText() }
    }
}