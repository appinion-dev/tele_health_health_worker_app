package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PrescriptionsModel : ResponseModel() {
    @SerializedName("prescriptions")
    @Expose
    var prescriptions: List<Prescription>? = null
}