package com.aah.sftelehealthworker.models.login

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Keep
@Entity(tableName = "HealthWorker")
class HealthWorker {
    @SerializedName("name")
    @Expose
    @ColumnInfo(defaultValue = "")
    var name: String = ""

    @SerializedName("staffId")
    @Expose
    @PrimaryKey
    @NotNull
    @ColumnInfo(defaultValue = "")
    var staffId: String = ""

    @SerializedName("isdCode")
    @Expose
    @ColumnInfo(defaultValue = "")
    var isdCode: String = ""

    @SerializedName("phone")
    @Expose
    @ColumnInfo(defaultValue = "")
    var phone: String = ""

    @SerializedName("gender")
    @Expose
    @ColumnInfo(defaultValue = "")
    var gender: String = ""

    @SerializedName("image")
    @Expose
    @ColumnInfo(defaultValue = "")
    var image: String = ""

    @SerializedName("isActive")
    @Expose
    @ColumnInfo(defaultValue = "")
    var active: Boolean? = null

    @SerializedName("token")
    @Expose
    @ColumnInfo(defaultValue = "")
    var token: String = ""

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

    @SerializedName("branch")
    @Expose
    @Embedded(prefix = "branch_")
    var branch: Branch? = null
}