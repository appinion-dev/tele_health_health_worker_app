package com.aah.sftelehealthworker.ui.doctorList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.appoinment.Category
import com.aah.sftelehealthworker.models.appoinment.Doctor
import com.aah.sftelehealthworker.network.networkService.AppointmentService
import com.aah.sftelehealthworker.utils.PAGE_NO
import com.aah.sftelehealthworker.utils.SIZE

class DoctorListViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val doctorMutableLiveData = MutableLiveData<List<Doctor>>()

    fun loadData(categoryId: String) {
        refresh.value = true
        AppointmentService.requestDoctorList(
            MutableLiveData(),
            getToken(),
            categoryId,
            getBranchId(),
            PAGE_NO,
            SIZE
        ).observeForever { doctorsModel ->
            if (doctorsModel.status == 200) {
                doctorMutableLiveData.value = doctorsModel.doctors
                isSuccessFull.value = true
            } else {
                isSuccessFull.value = false
                message.value = doctorsModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken(): String {
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance())
            .daoAccess().getToken()
    }

    private fun getBranchId(): String {
        return AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess()
            .getBranchId().toString()
    }
}