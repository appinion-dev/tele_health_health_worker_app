package com.aah.sftelehealthworker.models.home

import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PatientProfileModel : ResponseModel() {
    @SerializedName("data")
    @Expose
    val patients: List<Patient>? = null

    @SerializedName("patient")
    @Expose
    val patientProfile: PatientProfile? = null
}