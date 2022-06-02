package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VitalsSubmitModel : ResponseModel() {
    @SerializedName("patientId")
    @Expose
    var patientId = 0

    @SerializedName("bloodPressure")
    @Expose
    var bloodPressure: String? = null

    @SerializedName("pulseRate")
    @Expose
    var pulseRate: String? = null

    @SerializedName("respirationRate")
    @Expose
    var respirationRate: String? = null

    @SerializedName("bloodSuger")
    @Expose
    var bloodSuger: String? = null

    @SerializedName("bloodTemperature")
    @Expose
    var bloodTemperature: String? = null

    @SerializedName("bodyWeight")
    @Expose
    var bodyWeight: String? = null
}