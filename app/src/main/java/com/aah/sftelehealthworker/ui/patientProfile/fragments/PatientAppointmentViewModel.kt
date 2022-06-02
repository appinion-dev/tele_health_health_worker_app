package com.aah.sftelehealthworker.ui.patientProfile.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.DataResource
import com.aah.sftelehealthworker.models.home.Appointment
import com.aah.sftelehealthworker.models.patient.PatientAppointment
import com.aah.sftelehealthworker.network.networkService.HomeService
import com.aah.sftelehealthworker.repository.PatientRepository
import java.util.*

class PatientAppointmentViewModel : ViewModel() {


    fun loadData(patientId: String, status: String, date: String) {
        PatientRepository.requestPatientAppointmentList(getToken(), patientId, status, date)
    }

    fun observeAppointmentData(): LiveData<DataResource<PatientAppointment>> {
        return PatientRepository.appointmentModel
    }

    private fun getToken(): String {
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance())
            .daoAccess().getToken()
    }
}