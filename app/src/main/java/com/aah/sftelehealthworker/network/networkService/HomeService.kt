package com.aah.sftelehealthworker.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.home.AppointmentModel
import com.aah.sftelehealthworker.models.home.PatientProfileModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.models.login.PhoneNo
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import com.aah.sftelehealthworker.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HomeService {
    @Synchronized
    fun requestPatientWithPhone(patientProfileModel: MutableLiveData<PatientProfileModel>, token :String, branchId :String, phoneNo :String): MutableLiveData<PatientProfileModel> {

        val call: Call<PatientProfileModel> = ApiClient.getApi().requestBranchWisePatients(token, branchId, "1", "10000", phoneNo)
        call.enqueue(object : Callback<PatientProfileModel> {
            override fun onResponse(call: Call<PatientProfileModel>?, response: Response<PatientProfileModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as PatientProfileModel
                    temp.status = response.code()
                    patientProfileModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = PatientProfileModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())

                        temp.error = response.error
                        temp.status = response.status
                        patientProfileModel.postValue(temp)
                    }
                    else{
                        patientProfileModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<PatientProfileModel>?, t: Throwable?) {
                val failed = PatientProfileModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                patientProfileModel.postValue(failed)
            }
        })
        return patientProfileModel
    }

    @Synchronized
    fun requestAppointmentList(appointmentModel: MutableLiveData<AppointmentModel>, token :String, branchId :String, status :String ,phoneNo :String,date:String): MutableLiveData<AppointmentModel> {

        val call: Call<AppointmentModel> = ApiClient.getApi().requestAppointmentList(token, branchId, "1", "10000", status,phoneNo,date)
        call.enqueue(object : Callback<AppointmentModel> {
            override fun onResponse(call: Call<AppointmentModel>?, response: Response<AppointmentModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as AppointmentModel
                    temp.status = response.code()
                    appointmentModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = AppointmentModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        appointmentModel.postValue(temp)
                    }
                    else{
                        appointmentModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<AppointmentModel>?, t: Throwable?) {
                val failed = AppointmentModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                appointmentModel.postValue(failed)
            }
        })
        return appointmentModel
    }
}