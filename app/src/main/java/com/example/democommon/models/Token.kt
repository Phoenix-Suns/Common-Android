package com.vn.onewayradio.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Token(@SerializedName("expiresIn")
                 val expiresIn: String = "",
                 @SerializedName("tokenType")
                 val tokenType: String = "",
                 @SerializedName("accessToken")
                 val accessToken: String = "",
                 @SerializedName("refreshToken")
                 val refreshToken: String = "") : Serializable


