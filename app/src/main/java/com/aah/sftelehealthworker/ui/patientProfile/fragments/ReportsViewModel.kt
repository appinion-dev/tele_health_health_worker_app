package com.aah.sftelehealthworker.ui.patientProfile.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.newPatient.Report
import com.aah.sftelehealthworker.network.networkService.PatientService

class ReportsViewModel : ViewModel() {

    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val reportMutableLiveData = MutableLiveData<List<Report>>()


    fun loadData(patientId: String) {
        refresh.value = true
//        refresh.value = false

        PatientService.requestReports(MutableLiveData(), getToken(), patientId).observeForever {
//            if(it.reports.isNullOrEmpty()){
//                reportMutableLiveData.value = emptyList()
//            }
//            else{
//                it.reports
//            }
            reportMutableLiveData.value = it.reports
            refresh.value = false
        }
//        AppointmentService.requestDoctorList(MutableLiveData(), getToken(), categoryId, PAGE_NO, SIZE).observeForever { doctorsModel ->
//            if (doctorsModel.status == 200) {
//                doctorMutableLiveData.value = doctorsModel.doctors
//                isSuccessFull.value = true
//            }
//            else {
//                isSuccessFull.value = false
//                message.value = doctorsModel.error
//            }
//            refresh.value = false
//        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}