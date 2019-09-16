package com.example.democommon.app

import com.nghiatl.common.Prefs

const val PREF_KEY_TOKEN = "pref_key_token"
const val PREF_KEY_REFRESH_TOKEN = "pref_key_refresh_token"
const val PREF_KEY_EMAIL = "pref_key_email"

object AppPrefs {
    fun saveAccessToken(token: String?) {
        Prefs.putString(PREF_KEY_TOKEN, token)
    }

    fun getAccessToken(): String {
        return Prefs.getString(PREF_KEY_TOKEN, "")
    }

    fun saveRefreshToken(token: String?) {
        Prefs.putString(PREF_KEY_REFRESH_TOKEN, token)
    }


    fun getRefreshToken(): String {
        return Prefs.getString(PREF_KEY_REFRESH_TOKEN, "")
    }


    fun saveEmail(email: String?) {
        Prefs.putString(PREF_KEY_EMAIL, email)
    }


    fun getEmail(): String {
        return Prefs.getString(PREF_KEY_EMAIL, "")
    }
}