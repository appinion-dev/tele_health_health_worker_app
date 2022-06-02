package com.aah.sftelehealthworker.ui.patientProfile.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.newPatient.Vital
import com.aah.sftelehealthworker.network.networkService.PatientService

class VitalsViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val vitalMutableLiveData = MutableLiveData<List<Vital>>()

    fun loadData(patientId: String) {
        refresh.value = true

        PatientService.requestVitals(MutableLiveData(), getToken(), patientId).observeForever(){
            vitalMutableLiveData.postValue(it.vitals)
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