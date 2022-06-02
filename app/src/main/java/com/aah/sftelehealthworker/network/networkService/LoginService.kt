package com.aah.sftelehealthworker.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.login.HealthWorkerModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.models.login.PhoneNo
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import com.aah.sftelehealthworker.utils.AppUtils
import com.aah.sftelehealthworker.utils.CLIENT_SECRET
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginService {
    @Synchronized
    fun loginWithPhoneRequest(
        otpModel: MutableLiveData<OtpModel>,
        phoneNo: String
    ): MutableLiveData<OtpModel> {
        val phone = PhoneNo()
        phone.phone = phoneNo.toString()
        val call: Call<OtpModel> = ApiClient.getApi().requestWithLoginPhoneNo(phone)
        call.enqueue(object : Callback<OtpModel> {
            override fun onResponse(call: Call<OtpModel>?, response: Response<OtpModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as OtpModel
                    temp.status = response.code()
                    otpModel.postValue(temp)
                } else {
                    if (response.body() == null) {
                        val temp = OtpModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.message = response.error
                        temp.status = response.status
                        otpModel.postValue(temp)
                    } else {
                        otpModel.postValue(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<OtpModel>?, t: Throwable?) {
                val failed = OtpModel()
                failed.message = "Couldn't Connect"
                failed.status = 4
                otpModel.postValue(failed)
            }
        })
        return otpModel;
    }

    @Synchronized
    fun requestHealthWorker(
        healthWorkerModel: MutableLiveData<HealthWorkerModel>,
        phoneNo: String,
        otp: String
    ): MutableLiveData<HealthWorkerModel> {
        val phone = PhoneNo()
        phone.phone = phoneNo.toString()
        phone.otp = otp.toString()
        val call: Call<HealthWorkerModel> = ApiClient.getApi().requestHealthWorker(phone)
        call.enqueue(object : Callback<HealthWorkerModel> {
            override fun onResponse(
                call: Call<HealthWorkerModel>?,
                response: Response<HealthWorkerModel>?
            ) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as HealthWorkerModel
                    temp.status = response.code()
                    healthWorkerModel.postValue(temp)
                } else {
                    if (response.body() == null) {
                        val temp = HealthWorkerModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        healthWorkerModel.postValue(temp)
                    } else {
                        healthWorkerModel.postValue(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<HealthWorkerModel>?, t: Throwable?) {
                val failed = HealthWorkerModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                healthWorkerModel.postValue(failed)
            }
        })
        return healthWorkerModel
    }
}