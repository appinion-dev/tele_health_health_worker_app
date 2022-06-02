package com.aah.sftelehealthworker.models.doctor

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CallbackCost {
    @SerializedName("doctorId")
    @Expose
    var doctorId = 0

    @SerializedName("timeslotId")
    @Expose
    var timeslotId = 0

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("callbackRate")
    @Expose
    var callbackRate: String? = null

    @SerializedName("followUpRate")
    @Expose
    var followUpRate: String? = null
}