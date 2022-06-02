package com.aah.sftelehealthworker.models.appoinment

import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.doctor.CallbackCost
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorsModel: ResponseModel() {
    @SerializedName("doctors")
    @Expose
    var doctors: List<Doctor>? = null
}