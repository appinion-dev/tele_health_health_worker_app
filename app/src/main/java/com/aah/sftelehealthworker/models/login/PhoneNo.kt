package com.aah.sftelehealthworker.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhoneNo {
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("otp")
    @Expose
    var otp: String? = null

    constructor() {}
}