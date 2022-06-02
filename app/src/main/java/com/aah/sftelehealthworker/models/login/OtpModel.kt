package com.aah.sftelehealthworker.models.login

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OtpModel : ResponseModel {

    @SerializedName("success")
    @Expose
    var success: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("otp")
    @Expose
    var otp = 0

    constructor() {}

    constructor(success: String?, message: String?, otp: Int) {
        this.success = success
        this.message = message
        this.otp = otp
    }
}