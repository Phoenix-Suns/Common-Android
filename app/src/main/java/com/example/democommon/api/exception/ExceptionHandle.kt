package com.vn.onewayradio.api.exception

import android.content.Intent
import android.util.Log
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

object ExceptionHandle {

    var errorCode = ErrorStatus.ErrorCode.DEFAULT_ERROR
    var errorMsg = ErrorStatus.ErrorMessage.DEFAULT_ERROR

    fun handleException(e: Throwable): String {
        e.printStackTrace()
        e.printStackTrace()

        if (e is SocketTimeoutException) {
            Timber.e("Kết nối không thành công: %s", e.message)
            errorMsg = ErrorStatus.ErrorMessage.TIMEOUT_ERROR
            errorCode = ErrorStatus.ErrorCode.TIMEOUT_ERROR

        } else if (e is ConnectException) {
            Timber.e("Không có kết nối mạng: %s", e.message)
            errorMsg = ErrorStatus.ErrorMessage.NETWORK_ERROR
            errorCode = ErrorStatus.ErrorCode.NETWORK_ERROR

        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            Timber.e("ParseException: %s", e.message)
            errorMsg = ErrorStatus.ErrorMessage.PARSE_ERROR
            errorCode = ErrorStatus.ErrorCode.PARSE_ERROR

        } else if (e is ApiException) {
            errorMsg = e.message.toString()
            errorCode = ErrorStatus.ErrorCode.API_ERROR

        } else if (e is UnknownHostException) {
            Timber.e("Lỗi kết nối: %s", e.message)
            errorMsg = ErrorStatus.ErrorMessage.UNKNOWN_HOST_ERROR
            errorCode = ErrorStatus.ErrorCode.UNKNOWN_HOST_ERROR

        } else if (e is IllegalArgumentException) {
            errorMsg = ErrorStatus.ErrorMessage.ILLEGAL_ARGUMENT_ERROR
            errorCode = ErrorStatus.ErrorCode.ILLEGAL_ARGUMENT_ERROR

        } else {
            if (e is HttpException) {
                try {
                    errorMsg = e.message()
                    errorCode = e.code()

                    val errorBody = e.response()?.errorBody()
                    try {
                        val jsonString = errorBody?.string()
                        val parser = JsonParser()
                        val jsonElement = parser.parse(jsonString)
                        if (jsonElement != null) {
                            val jsonObj = jsonElement.asJsonObject
                            errorMsg = jsonObj.get("message").toString()
                            errorCode = jsonObj.get("code").asInt
                        }
                    } catch (ex: JsonSyntaxException) {
                        ex.printStackTrace()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                } catch (e1: Exception) {
                    Timber.e("Lỗi không xác định: ${e1.message}")
                }

                if (errorCode == 401) {
                    /*val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                    errorMsg = context.getString(R.string.notify_please_login_again)*/
                }
            }
        }

        return errorMsg
    }
}