package com.aah.sftelehealthworker.ui.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.login.HealthWorker
import com.aah.sftelehealthworker.models.login.HealthWorkerModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.network.networkService.LoginService

class OtpViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val healthWorkerLiveData = MutableLiveData<HealthWorkerModel>()
    val otpLiveData = MutableLiveData<OtpModel>()

    fun loadData(phoneNo: String, otp:String) {
        refresh.value = true
        LoginService.requestHealthWorker(MutableLiveData(), phoneNo,otp).observeForever { healthWorkerModel ->
            if (healthWorkerModel.status == 200) {
                healthWorkerModel.healthWorker?.token = healthWorkerModel.token
                healthWorkerLiveData.value = healthWorkerModel
                healthWorkerModel.healthWorker?.let { saveHealthWorker(it) }
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = healthWorkerModel.error
            }
            refresh.value = false
        }
    }

    fun resendOtp(phoneNo: String) {
        refresh.value = true
        LoginService.loginWithPhoneRequest(MutableLiveData(), phoneNo).observeForever { otpModel ->
            if (otpModel.status == 200) {
                otpLiveData.value = otpModel
            }
            else {
                message.value = otpModel.message
            }
            refresh.value = false
        }
    }

    private fun saveHealthWorker(healthWorker: HealthWorker) {
        AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().insertHealthWorker(healthWorker)
    }
}