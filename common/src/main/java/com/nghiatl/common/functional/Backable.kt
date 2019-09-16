package com.nghiatl.common.functional

import androidx.fragment.app.Fragment
import com.nghiatl.common.extension.findChildVisible

interface Backable {
    fun onBackPressed(): Boolean {
        if (this is Fragment && (findChildVisible() as? Backable)?.onBackPressed() == true) return true
        return false
    }
}