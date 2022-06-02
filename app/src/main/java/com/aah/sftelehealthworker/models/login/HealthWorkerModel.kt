package com.aah.sftelehealthworker.models.login

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HealthWorkerModel : ResponseModel() {
    @SerializedName("healthWorker")
    @Expose
    var healthWorker: HealthWorker? = null

    @SerializedName("token")
    @Expose
    var token: String = ""
}