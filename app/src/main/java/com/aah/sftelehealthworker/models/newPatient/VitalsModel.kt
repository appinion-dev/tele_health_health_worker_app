package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VitalsModel : ResponseModel() {
    @SerializedName("vitals")
    @Expose
    var vitals: List<Vital>? = null
}