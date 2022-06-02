package com.aah.sftelehealthworker.ui.home.appointments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.home.Appointment
import com.aah.sftelehealthworker.network.networkService.HomeService

class AppointmentsViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val appointmentMutableLiveData = MutableLiveData<List<Appointment>>()

    fun loadData(status :String, phoneNo: String,date:String) {
        refresh.value = true
        HomeService.requestAppointmentList(MutableLiveData(), getToken(), getBranchId(), status,phoneNo,date).observeForever { appointmentModel ->
            if (appointmentModel.status == 200) {
                appointmentMutableLiveData.value = appointmentModel.appointments
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = appointmentModel.error
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