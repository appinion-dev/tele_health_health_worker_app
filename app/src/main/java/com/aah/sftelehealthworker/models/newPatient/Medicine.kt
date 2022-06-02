package com.aah.sftelehealthworker.models.newPatient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Medicine :Serializable{
    @SerializedName("dosage")
    @Expose
    var dosage: String? = null

    @SerializedName("duration")
    @Expose
    var duration: Int? = null

    @SerializedName("instruction")
    @Expose
    var instruction: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
}