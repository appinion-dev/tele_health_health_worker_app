package com.aah.sftelehealthworker.models.newPatient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Upazilla {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("bnName")
    @Expose
    var bnName: String? = null

    @SerializedName("districtId")
    @Expose
    var districtId = 0

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
}