package com.aah.sftelehealthworker.ui.verifyPatientMobileNumber

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.network.networkService.LoginService
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService

class VerifyPatientMobileNumberViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val otpLiveData = MutableLiveData<OtpModel>()

    fun setSuccessFullFalse(){
        isSuccessFull.value = false
    }

    fun loadData(phoneNo: String) {
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
}