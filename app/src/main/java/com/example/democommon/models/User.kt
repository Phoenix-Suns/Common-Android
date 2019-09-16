package com.vn.onewayradio.model


import com.google.gson.annotations.SerializedName
import com.nghiatl.common.ObjectUtil
import java.io.Serializable

class User() : Serializable {

    @SerializedName("role")
    var role: String = ""
    @SerializedName("is_active")
    var isActive: Boolean = false
    @SerializedName("dial_code")
    var dialCode: String = ""
    @SerializedName("sex")
    var sex: String = ""
    @SerializedName("name")
    var name: String =""
    @SerializedName("created_at")
    var createdAt: String = ""
    @SerializedName("_id")
    var id: String = ""
    @SerializedName("type")
    var type: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("phone")
    var phone: String = ""
    @SerializedName("picture")
    var picture: String = ""
    @SerializedName("dob")
    var dob: String = ""
    @SerializedName("total_follower")
    var totalFollower: Int = 0
    @SerializedName("total_following")
    var totalFollowing: Int = 0
    @SerializedName("is_follow")
    var isFollow: Boolean = false
}


