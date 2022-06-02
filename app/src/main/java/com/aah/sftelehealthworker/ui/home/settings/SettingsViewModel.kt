package com.aah.sftelehealthworker.ui.home.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.login.HealthWorker

class SettingsViewModel : ViewModel() {

    val healthWorkerMutableLiveData = MutableLiveData<HealthWorker>()
    fun getHealthWorker(){
        var healthWorkerList = AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getHealthWorker()
        healthWorkerMutableLiveData.value = healthWorkerList[0]

    }
    fun delete(){
        AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().deleteHealthWorker()
    }
}