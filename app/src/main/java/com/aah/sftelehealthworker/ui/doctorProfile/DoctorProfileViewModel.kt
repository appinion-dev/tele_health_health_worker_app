package com.aah.sftelehealthworker.ui.doctorProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.appoinment.CreateAppointmentModel
import com.aah.sftelehealthworker.models.appoinment.DoctorsInfoModel
import com.aah.sftelehealthworker.models.home.PatientProfileModel
import com.aah.sftelehealthworker.models.newPatient.District
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.network.networkService.AppointmentService
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorProfileViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()

    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val doctorsInfoModelMutableLiveData = MutableLiveData<DoctorsInfoModel>()
    val responseModelMutableLiveData = MutableLiveData<ResponseModel>()

    fun setSuccessFullFalse(){
        isSuccessFull.value = false
    }

    fun loadData(doctorId: String, timeslotId: String){
        AppointmentService.requestDoctorPatientDetails(MutableLiveData(),getToken(), doctorId, timeslotId).observeForever { doctorsInfoModel ->
            if ( doctorsInfoModel.status == 200) {
                doctorsInfoModelMutableLiveData.value = doctorsInfoModel
//                isSuccessFull.value = true
//                uploadImage(patientProfileModel, uri)
            }
            else {
//                isSuccessFull.value = false
                message.value = doctorsInfoModel.error
            }
            refresh.value = false
        }
    }

    fun createAppointment(doctorId: Int, patientId: Int, timeSlotId: Int, date: String, doctorCategoryId: Int) {
        refresh.value = true
        val createAppointmentModel = CreateAppointmentModel()
        createAppointmentModel.doctorId = doctorId
        createAppointmentModel.patientId = patientId
        createAppointmentModel.timeSlotId = timeSlotId
        createAppointmentModel.video = true
        createAppointmentModel.date = date
        createAppointmentModel.doctorCategoryId = doctorCategoryId


        AppointmentService.requestCreateAppointment(MutableLiveData(),getToken(), createAppointmentModel).observeForever { responseModel ->
            if ( responseModel.status == 201) {
                responseModelMutableLiveData.value = responseModel
                isSuccessFull.value = true
//                uploadImage(patientProfileModel, uri)
            }
            else {
                isSuccessFull.value = false
                message.value = responseModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}