package com.aah.sftelehealthworker.models.appoinment

import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.doctor.CallbackCost
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorsInfoModel : ResponseModel() {
    @SerializedName("doctor")
    @Expose
    var doctor: Doctor? = null

    @SerializedName("advices")
    @Expose
    var advices: List<Any>? = null

    @SerializedName("documents")
    @Expose
    var documents: List<Any>? = null

    @SerializedName("callbackCost")
    @Expose
    var callbackCost: CallbackCost? = null
}