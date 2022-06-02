package com.aah.sftelehealthworker.models.newPatient

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Report {
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

    @SerializedName("patientId")
    @Expose
    var patientId: Int? = null

    @SerializedName("documentCategoryId")
    @Expose
    var documentCategoryId: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("previewUrl")
    @Expose
    var previewUrl: String? = null

    @SerializedName("note")
    @Expose
    var note: String? = null

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