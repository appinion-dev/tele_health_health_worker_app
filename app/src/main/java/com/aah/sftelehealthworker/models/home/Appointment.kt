package com.aah.sftelehealthworker.models.home

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Appointment {
//    var id: String? = null
//    var name: String? = null
//    var date: String? = null
//    var statusMessage: String? = null
//
//    constructor() {}
//    constructor(id: String?, name: String?, date: String?, statusMessage: String?) {
//        this.id = id
//        this.name = name
//        this.date = date
//        this.statusMessage = statusMessage
//    }

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("branchId")
    @Expose
    val branchId: String? = null

    @SerializedName("callbackDate")
    @Expose
    val callbackDate: String? = null

    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("timeslotId")
    @Expose
    val timeslotId: Int? = null

    @SerializedName("isVideo")
    @Expose
    val isVideo: Int? = null

    @SerializedName("startTime")
    @Expose
    val startTime: String? = null

    @SerializedName("endTime")
    @Expose
    val endTime: String? = null

    @SerializedName("patientName")
    @Expose
    val patientName: String? = null

    @SerializedName("doctorName")
    @Expose
    val doctorName: String? = null

    @SerializedName("doctorTimezone")
    @Expose
    val doctorTimezone: String? = null

    @SerializedName("patientImage")
    @Expose
    val patientImage: String? = null

    @SerializedName("patientId")
    @Expose
    val patientId: String? = null

    @SerializedName("caseId")
    @Expose
    val caseId: String? = null

    @SerializedName("isAttanachmentAvailable")
    @Expose
    val isAttachmentAvailable :Boolean?=  null

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}