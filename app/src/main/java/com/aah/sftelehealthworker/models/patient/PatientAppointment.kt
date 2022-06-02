package com.aah.sftelehealthworker.models.patient

import com.aah.sftelehealthworker.models.ResponseModel

data class PatientAppointment   (
    val appointments: List<Appointment>
): ResponseModel()