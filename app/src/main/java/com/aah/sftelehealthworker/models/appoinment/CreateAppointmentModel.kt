package com.aah.sftelehealthworker.models.appoinment

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateAppointmentModel : ResponseModel() {
    @SerializedName("doctorId")
    @Expose
    var doctorId = 0

    @SerializedName("patientId")
    @Expose
    var patientId = 0

    @SerializedName("timeslotId")
    @Expose
    var timeSlotId = 0

    @SerializedName("isVideo")
    @Expose
    var video: Boolean? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("doctorCategoryId")
    @Expose
    var doctorCategoryId = 0

    override fun equals(obj: Any?): Boolean {
        return super.equals(obj)
    }
}