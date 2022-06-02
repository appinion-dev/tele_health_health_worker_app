package com.aah.sftelehealthworker.models.patient

data class Appointment(
    val callbackDate: String,
    val caseId: Int,
    val doctorId: Int,
    val doctorImage: String,
    val doctorName: String,
    val doctorTimezone: String,
    val documentUrl: Boolean,
    val endTime: String,
    val id: Int,
    val patientId: Int,
    val startTime: String,
    val status: String,
    val timeslotId: Int
)