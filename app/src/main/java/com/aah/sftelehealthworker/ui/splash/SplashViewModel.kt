package com.aah.sftelehealthworker.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase

class SplashViewModel : ViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    fun checkLogin(){
        if(getToken().isNullOrEmpty()){
            isLogin.value = false
        }
        else{
            isLogin.value = true
        }
    }

    private fun getToken():String{
        return AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}