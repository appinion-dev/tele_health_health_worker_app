package com.aah.sftelehealthworker.ui.home.patients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.home.Patient
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.network.networkService.HomeService
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService

class PatientsViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val patientMutableLiveData = MutableLiveData<List<Patient>>()

    fun loadData(phoneNo: String) {
        refresh.value = true
        HomeService.requestPatientWithPhone(MutableLiveData(), getToken(), getBranchId(), phoneNo).observeForever { patientProfileModel ->
            if (patientProfileModel.status == 200) {
                patientMutableLiveData.value = patientProfileModel.patients
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = patientProfileModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }

    private fun getBranchId():String{
        return AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getBranchId().toString()
    }
}