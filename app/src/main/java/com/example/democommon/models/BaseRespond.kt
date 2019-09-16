package com.example.democommon.models

import com.google.gson.annotations.SerializedName

data class BaseRespond<T>(@SerializedName("code")
                   val code: Int,
                          @SerializedName("message")
                   val message: String = "",
                          @SerializedName("data")
                   val data: T)