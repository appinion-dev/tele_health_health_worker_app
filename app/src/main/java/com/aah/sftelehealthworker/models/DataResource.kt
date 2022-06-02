package com.aah.sftelehealthworker.models

import com.google.gson.Gson
import java.lang.Exception

class DataResource<T>(val status: DataStatus, val data: T?, val message: String?) {

    enum class DataStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        open fun <T> success(data: T): DataResource<T> {
            return DataResource(DataStatus.SUCCESS, data, null)
        }

        open fun <T> error(msg: String): DataResource<T> {
            return DataResource(DataStatus.ERROR, null, msg)
        }

        open fun <T> parseError(res: String): DataResource<T> {
            try {
                val response = Gson().fromJson(res, ResponseModel::class.java)
                return DataResource(DataStatus.ERROR, null, response.error)
            } catch (e: Exception) {
                e.printStackTrace()
                // showErrorToast("invalid response")
                return DataResource(DataStatus.ERROR, null, "invalid response")
            }
        }

        open fun <T> loading(): DataResource<T> {
            return DataResource(DataStatus.LOADING, null, null)
        }


    }

}