package com.aah.sftelehealthworker.models.home

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AppointmentModel : ResponseModel() {
    @SerializedName("appointments")
    @Expose
    val appointments: List<Appointment>? = null
}