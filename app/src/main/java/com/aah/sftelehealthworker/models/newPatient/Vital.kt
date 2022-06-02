package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Vital {
//    @SerializedName("id")
//    @Expose
//    var id: String? = null
//
//    @SerializedName("name")
//    @Expose
//    var name: String? = null
//
//    @SerializedName("date")
//    @Expose
//    var date: String? = null

    @SerializedName("patientId")
    @Expose
    var patientId: Int? = null

    @SerializedName("bloodTemperature")
    @Expose
    var bloodTemperature: String? = null

    @SerializedName("pulseRate")
    @Expose
    var pulseRate: String? = null

    @SerializedName("respirationRate")
    @Expose
    var respirationRate: String? = null

    @SerializedName("bloodPressure")
    @Expose
    var bloodPressure: String? = null

    @SerializedName("bodyWeight")
    @Expose
    var bodyWeight: String? = null

    @SerializedName("bloodSuger")
    @Expose
    var bloodSuger: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
}