package com.aah.sftelehealthworker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ResponseModel {
    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    constructor() {}
    constructor(error: String?, status: Int?) {
        this.error = error
        this.status = status
    }
}