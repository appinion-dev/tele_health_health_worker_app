package com.aah.sftelehealthworker.models.appoinment

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeSlotsModel : ResponseModel() {
    @SerializedName("timeslots")
    @Expose
    var timeSlots: List<TimeSlot>? = null
}