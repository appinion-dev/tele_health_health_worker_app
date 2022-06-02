package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DistrictUpazillasModel:ResponseModel() {
    @SerializedName("data")
    @Expose
    var districts: List<District>? = null
}