package com.aah.sftelehealthworker.models.appoinment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeSlot {
    var doctorName: String? = null

    @SerializedName("timeslotId")
    @Expose
    var timeslotId = 0

    @SerializedName("startTime")
    @Expose
    var startTime: String? = null

    @SerializedName("endTime")
    @Expose
    var endTime: String? = null

    @SerializedName("weekday")
    @Expose
    var weekday = 0

    @SerializedName("callbackRate")
    @Expose
    var callbackRate = 0

    @SerializedName("followUpRate")
    @Expose
    var followUpRate = 0

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}