package com.aah.sftelehealthworker.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.network.networkService.LoginService

class LoginViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val otpLiveData = MutableLiveData<OtpModel>()

    fun loadData(phoneNo: String) {
        refresh.value = true
        LoginService.loginWithPhoneRequest(MutableLiveData(), phoneNo).observeForever { otpModel ->
            if (otpModel.status == 200) {
                if (otpModel.error != null) {
                    isSuccessFull.value = false
                    message.value = otpModel.error
                } else {
                    otpLiveData.value = otpModel
                    isSuccessFull.value = true
                }
            } else {
                isSuccessFull.value = false
                message.value = otpModel.message
            }
            refresh.value = false
        }
    }
}