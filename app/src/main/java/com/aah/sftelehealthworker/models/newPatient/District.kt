package com.aah.sftelehealthworker.models.newPatient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class District {
    @SerializedName("upazillas")
    @Expose
    var upazillas: List<Upazilla>? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("bnName")
    @Expose
    var bnName: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
}