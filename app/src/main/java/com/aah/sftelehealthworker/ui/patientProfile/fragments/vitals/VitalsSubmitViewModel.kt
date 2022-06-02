package com.aah.sftelehealthworker.ui.patientProfile.fragments.vitals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.newPatient.Vital
import com.aah.sftelehealthworker.models.newPatient.VitalsSubmitModel
import com.aah.sftelehealthworker.network.networkService.PatientService

class VitalsSubmitViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
//    val vitalMutableLiveData = MutableLiveData<List<Vital>>()

    fun setSuccessfulFalse(){
        isSuccessFull.value = false
    }

    fun submitVitals(patientId: Int, bloodTemperature: String, pulseRate: String, respirationRate: String, bloodPressure: String, bodyWeight: String, bloodSuger: String) {
        refresh.value = true

        val vitalsSubmitModel = VitalsSubmitModel()
        vitalsSubmitModel.patientId = patientId
        vitalsSubmitModel.bloodTemperature = bloodTemperature
        vitalsSubmitModel.pulseRate = pulseRate
        vitalsSubmitModel.respirationRate = respirationRate
        vitalsSubmitModel.bloodPressure = bloodPressure
        vitalsSubmitModel.bodyWeight = bodyWeight
        vitalsSubmitModel.bloodSuger = bloodSuger

        if(bloodTemperature.isNullOrEmpty() && pulseRate.isNullOrEmpty() && respirationRate.isNullOrEmpty() && bloodPressure.isNullOrEmpty() && bodyWeight.isNullOrEmpty() && bloodSuger.isNullOrEmpty()){
            isSuccessFull.value = false
            message.value = "Please fill at least one field"
        }
        else {

            PatientService.submitVitals(MutableLiveData(), getToken(), vitalsSubmitModel)
                .observeForever { doctorsModel ->
                    if (doctorsModel.status == 201) {
//                doctorMutableLiveData.value = doctorsModel.doctors
                        isSuccessFull.value = true
                    } else {
                        isSuccessFull.value = false
                        message.value = doctorsModel.error
                    }
                    refresh.value = false
                }
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}