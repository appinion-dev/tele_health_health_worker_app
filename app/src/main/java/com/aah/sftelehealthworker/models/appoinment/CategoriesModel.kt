package com.aah.sftelehealthworker.models.appoinment

import com.aah.sftelehealthworker.models.ResponseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoriesModel : ResponseModel() {
    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null
}