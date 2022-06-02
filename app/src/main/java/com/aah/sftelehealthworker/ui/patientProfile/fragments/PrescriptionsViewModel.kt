package com.aah.sftelehealthworker.ui.patientProfile.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.newPatient.Prescription
import com.aah.sftelehealthworker.models.newPatient.PrescriptionDetails
import com.aah.sftelehealthworker.network.networkService.PatientService

class PrescriptionsViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val prescriptionsMutableLiveData = MutableLiveData<List<Prescription>>()

    fun loadData(patientId: String,caseId:String) {
        refresh.value = true

        PatientService.requestPrescription(MutableLiveData(), getToken(), patientId,caseId).observeForever {
            prescriptionsMutableLiveData.value = it.prescriptions
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