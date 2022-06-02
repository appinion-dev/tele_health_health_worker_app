package com.aah.sftelehealthworker.models.newPatient

import android.os.Parcel
import android.os.Parcelable
import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PatientProfile() : ResponseModel(),Parcelable {
    @SerializedName("userId")
    @Expose
    var userId = 0

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("upazillaId")
    @Expose
    var upazillaId = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null

    @SerializedName("dob")
    @Expose
    var dob: String? = null

    @SerializedName("height")
    @Expose
    var height = 0

    @SerializedName("weight")
    @Expose
    var weight = 0

    @SerializedName("bloodGroup")
    @Expose
    var bloodGroup: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("isActive")
    @Expose
    var active: Boolean? = null

    @SerializedName("branchId")
    @Expose
    var branchId = 0

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("age")
    @Expose
    var age : String? = null

    constructor(parcel: Parcel) : this() {
        userId = parcel.readInt()
        phone = parcel.readString()
        gender = parcel.readString()
        upazillaId = parcel.readInt()
        firstName = parcel.readString()
        lastName = parcel.readString()
        dob = parcel.readString()
        height = parcel.readInt()
        weight = parcel.readInt()
        bloodGroup = parcel.readString()
        image = parcel.readString()
        active = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        branchId = parcel.readInt()
        createdAt = parcel.readString()
        updatedAt = parcel.readString()
        id = parcel.readInt()
        age = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(phone)
        parcel.writeString(gender)
        parcel.writeInt(upazillaId)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(dob)
        parcel.writeInt(height)
        parcel.writeInt(weight)
        parcel.writeString(bloodGroup)
        parcel.writeString(image)
        parcel.writeValue(active)
        parcel.writeInt(branchId)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeInt(id)
        parcel.writeString(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientProfile> {
        override fun createFromParcel(parcel: Parcel): PatientProfile {
            return PatientProfile(parcel)
        }

        override fun newArray(size: Int): Array<PatientProfile?> {
            return arrayOfNulls(size)
        }
    }
}