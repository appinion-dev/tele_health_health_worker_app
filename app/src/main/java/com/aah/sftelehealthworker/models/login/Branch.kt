package com.aah.sftelehealthworker.models.login

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Keep
@Entity
class Branch {

    @SerializedName("name")
    @Expose
    @ColumnInfo(defaultValue = "")
    var name: String = ""

    @SerializedName("branchCode")
    @Expose
    @PrimaryKey
    @NotNull
    @ColumnInfo(defaultValue = "")
    var branchCode: String = ""

    @SerializedName("address")
    @Expose
    @ColumnInfo(defaultValue = "")
    var address: String = ""

    @SerializedName("isActive")
    @Expose
    @ColumnInfo(defaultValue = "")
    var active: Boolean? = null

    @SerializedName("boothCount")
    @Expose
    @ColumnInfo(defaultValue = "")
    var boothCount = 0

    @SerializedName("id")
    @Expose
    @ColumnInfo(defaultValue = "")
    var id = 0

    @SerializedName("createdAt")
    @Expose
    @ColumnInfo(defaultValue = "")
    var createdAt: String = ""

    @SerializedName("updatedAt")
    @Expose
    @ColumnInfo(defaultValue = "")
    var updatedAt: String = ""
}