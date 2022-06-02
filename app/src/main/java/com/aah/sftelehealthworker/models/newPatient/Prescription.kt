package com.aah.sftelehealthworker.models.newPatient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Prescription :Serializable{
    @SerializedName("prescription")
    @Expose
    var prescriptionDetails: PrescriptionDetails? = null

    @SerializedName("isFreeFollowUp")
    @Expose
    var isFreeFollowUp = 0

    @SerializedName("freeFollowUpRequested")
    @Expose
    var freeFollowUpRequested = 0

    @SerializedName("isRated")
    @Expose
    var isRated = 0

    @SerializedName("consultId")
    @Expose
    var consultId = 0

    @SerializedName("consultedAt")
    @Expose
    var consultedAt: String? = null

    @SerializedName("caseId")
    @Expose
    var caseId = 0

    @SerializedName("doctorCategoryId")
    @Expose
    var doctorCategoryId = 0
}