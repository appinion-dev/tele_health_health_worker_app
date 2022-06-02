package com.aah.sftelehealthworker.models.appoinment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Doctor {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null

    @SerializedName("regNo")
    @Expose
    var regNo: String? = null

    @SerializedName("signatureImage")
    @Expose
    var signatureImage: String? = null

    @SerializedName("doctorCategoryId")
    @Expose
    var doctorCategoryId = 0

    @SerializedName("qualification")
    @Expose
    var qualification: String? = null

    @SerializedName("workingSince")
    @Expose
    var workingSince = 0

    @SerializedName("isDisabled")
    @Expose
    var isDisabled = 0

    @SerializedName("connectNow")
    @Expose
    var connectNow = 0

    @SerializedName("surfaceCount")
    @Expose
    var surfaceCount = 0

    @SerializedName("enableTalk")
    @Expose
    var enableTalk = 0

    @SerializedName("visibleNonCorporate")
    @Expose
    var visibleNonCorporate = 0

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("isdCode")
    @Expose
    var isdCode: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null

    @SerializedName("hospital")
    @Expose
    var hospital: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("allowCallback")
    @Expose
    var allowCallback = 0

    @SerializedName("allowConnectNow")
    @Expose
    var allowConnectNow: String? = null

    @SerializedName("gcmapnsKey")
    @Expose
    var gcmapnsKey: String? = null

    @SerializedName("phoneOs")
    @Expose
    var phoneOs: String? = null

    @SerializedName("phoneOsVersion")
    @Expose
    var phoneOsVersion: String? = null

    @SerializedName("androidId")
    @Expose
    var androidId: String? = null

    @SerializedName("deviceDescription")
    @Expose
    var deviceDescription: String? = null

    @SerializedName("oneSignalId")
    @Expose
    var oneSignalId: String? = null

    @SerializedName("uninstallDate")
    @Expose
    var uninstallDate: String? = null

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

    @SerializedName("uninstalled")
    @Expose
    var uninstalled = 0

    @SerializedName("appVersion")
    @Expose
    var appVersion = 0.0

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("callbackRate")
    @Expose
    var callbackRate = 0

    @SerializedName("languages")
    @Expose
    var languages: String? = null

    @SerializedName("doctorCategories")
    @Expose
    var categories: List<Category>? = null

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}