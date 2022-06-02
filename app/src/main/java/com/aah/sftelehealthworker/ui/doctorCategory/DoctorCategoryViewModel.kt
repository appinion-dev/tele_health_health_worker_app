package com.aah.sftelehealthworker.ui.doctorCategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.appoinment.Category
import com.aah.sftelehealthworker.network.networkService.AppointmentService
import com.aah.sftelehealthworker.network.networkService.HomeService

class DoctorCategoryViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val categoryMutableLiveData = MutableLiveData<List<Category>>()

    fun loadData() {
        refresh.value = true
        AppointmentService.requestCategories(MutableLiveData(), getToken()).observeForever { categoriesModel ->
            if (categoriesModel.status == 200) {
                categoryMutableLiveData.value = categoriesModel.categories
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = categoriesModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}