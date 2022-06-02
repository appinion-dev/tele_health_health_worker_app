package com.aah.sftelehealthworker.models.newPatient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PrescriptionDetails:Serializable {
//    @SerializedName("id")
//    @Expose
//    var id: String? = null
//
//    @SerializedName("name")
//    @Expose
//    var name: String? = null
//
//    @SerializedName("date")
//    @Expose
//    var date: String? = null

    @SerializedName("isDietChart")
    @Expose
    var isDietChart: Boolean? = null

    @SerializedName("consultId")
    @Expose
    var consultId: Int? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("consultedAt")
    @Expose
    var consultedAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("medicine")
    @Expose
    var medicine: List<Medicine>? = null

    @SerializedName("dietChart")
    @Expose
    var dietChart: Any? = null

    @SerializedName("history")
    @Expose
    var history: String? = null

    @SerializedName("advice")
    @Expose
    var advice: String? = null

    fun getDocumentFileExtension(): String? {
        var dataType = "jpg"
        val i: Int = this.link!!.lastIndexOf('.')
        if (i > 0) {
            dataType = this.link!!.substring(i + 1)
        }
        return dataType
    }
}