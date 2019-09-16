package com.example.democommon.models

import com.google.gson.annotations.SerializedName
import com.vn.onewayradio.model.Token
import com.vn.onewayradio.model.User

class LoginRespond(@SerializedName("token")
                   val token: Token?,
                   @SerializedName("user")
                   val user: User?)