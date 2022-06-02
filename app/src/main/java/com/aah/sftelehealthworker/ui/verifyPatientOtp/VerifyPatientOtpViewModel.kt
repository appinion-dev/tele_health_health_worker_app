package com.aah.sftelehealthworker.ui.verifyPatientOtp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService

class VerifyPatientOtpViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val otpLiveData = MutableLiveData<OtpModel>()

    fun setSuccessFullFalse(){
        isSuccessFull.value = false
    }

    fun loadData(phoneNo: String, otp: String) {
        refresh.value = true
        VerifyPatientService.requestPatientWithOtp(MutableLiveData(),getToken(), phoneNo,otp).observeForever { otpModel ->
            if (otpModel.status == 200) {
//                otpLiveData.value = otpModel
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = otpModel.error
            }
            refresh.value = false
        }
    }

    fun resendOtp(phoneNo: String) {
        refresh.value = true
        VerifyPatientService.requestPatientWithPhone(MutableLiveData(), phoneNo).observeForever { otpModel ->
            if (otpModel.status == 200) {
                otpLiveData.value = otpModel
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = otpModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}