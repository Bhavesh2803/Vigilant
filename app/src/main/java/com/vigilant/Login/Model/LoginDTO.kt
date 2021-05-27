package com.vigilant.Login.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginDTO {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("refreshToken")
    @Expose
    var refreshToken: String? = null
}