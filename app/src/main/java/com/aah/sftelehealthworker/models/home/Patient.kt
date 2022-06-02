package com.aah.sftelehealthworker.models.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Patient {
//    var id: String? = null
//    var name: String? = null
//    var age: String? = null
//    var sex: String? = null
//
//    constructor() {}
//
//    constructor(id: String?, name: String?, age: String?, sex: String?) {
//        this.id = id
//        this.name = name
//        this.age = age
//        this.sex = sex
//    }
//
//    override fun equals(other: Any?): Boolean {
//        return super.equals(other)
//    }

    @SerializedName("id")
    @Expose
    var id: String = ""

    @SerializedName("phone")
    @Expose
    val phone: String = ""

    @SerializedName("firstName")
    @Expose
    val firstName: String = ""

    @SerializedName("lastName")
    @Expose
    val lastName: String = ""

    @SerializedName("gender")
    @Expose
    val gender: String = ""

    @SerializedName("age")
    @Expose
    var age: String? = null

    @SerializedName("image")
    @Expose
    val image: String = ""

    @SerializedName("userId")
    @Expose
    val userId: String = ""

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}