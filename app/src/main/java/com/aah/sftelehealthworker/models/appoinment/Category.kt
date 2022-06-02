package com.aah.sftelehealthworker.models.appoinment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("connectNowText")
    @Expose
    var connectNowText: String? = null

    @SerializedName("callbackText")
    @Expose
    var callbackText: String? = null

    @SerializedName("connectNowTime")
    @Expose
    var connectNowTime = 0

    @SerializedName("callbackTime")
    @Expose
    var callbackTime = 0

    @SerializedName("discount")
    @Expose
    var discount = 0

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("age")
    @Expose
    var age: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("logo")
    @Expose
    var logo: String? = null

    @SerializedName("isTestCategory")
    @Expose
    var isTestCategory = 0

    @SerializedName("allowConnectNow")
    @Expose
    var allowConnectNow = 0

    @SerializedName("allowCallback")
    @Expose
    var allowCallback = 0

    @SerializedName("allowPrescription")
    @Expose
    var allowPrescription = 0

    @SerializedName("allowVideo")
    @Expose
    var allowVideo = 0

    @SerializedName("isSpecialist")
    @Expose
    var isSpecialist = 0

    @SerializedName("specialist")
    @Expose
    private var specialist: String? = null

    @SerializedName("androidVersion")
    @Expose
    var androidVersion: String? = null

    @SerializedName("iosVersion")
    @Expose
    var iosVersion: String? = null

    @SerializedName("rate")
    @Expose
    var rate = 0

    @SerializedName("followUpRate")
    @Expose
    var followUpRate = 0

    @SerializedName("isAvailable")
    @Expose
    var isAvailable = 0

    @SerializedName("code")
    @Expose
    private val code: String? = null

    @SerializedName("allowVideoFollowUp")
    @Expose
    private val allowVideoFollowUp: Boolean? = null

    constructor() {}

    fun getSpecialist(): String? {
        return specialist
    }

    fun setSpecialist(specialist: String?) {
        this.specialist = specialist
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}