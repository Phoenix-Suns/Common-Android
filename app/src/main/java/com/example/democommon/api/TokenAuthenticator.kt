package com.example.democommon.api

import androidx.annotation.Nullable
import com.example.democommon.app.AppPrefs
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import java.io.IOException

class TokenAuthenticator : Authenticator {


    //Use to limit a number of request (only call twice request) authenticator
    private var requestCount: Int = 0

    private var isRefreshing = false

    @Nullable
    override fun authenticate(route: Route?, response: Response): Request? {
        /*if (responseCount(response) >= 2) {
            return null
        }*/
        synchronized(this) {
            requestCount += 1
            Timber.d(requestCount.toString())
            if (requestCount > 2) {
                requestCount = 0

                return null
            }

            val email = AppPrefs.getEmail()
            val token = AppPrefs.getAccessToken()
            val refreshToken = AppPrefs.getRefreshToken()

            val call = RetrofitManager.service.refreshToken(email, refreshToken)
            return try {
                val refreshTokenResponse = call.execute()
                if (refreshTokenResponse.code() == 200) {
                    requestCount = 0
                    isRefreshing = true

                    val newToken = refreshTokenResponse.body()
                    AppPrefs.saveAccessToken(newToken?.data?.token?.accessToken)
                    AppPrefs.saveRefreshToken(newToken?.data?.token?.refreshToken)
                    response.request.newBuilder()
                            .header("Authorization", newToken?.data?.token?.tokenType + " " + newToken?.data?.token?.accessToken)
                            .build()
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }

    }


    private fun responseCount(response: Response?): Int {
        var result = 1
        val priorResponse = response?.priorResponse
        while (priorResponse != null) {
            result++
        }
        return result
    }
}