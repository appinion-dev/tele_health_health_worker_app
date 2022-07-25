package com.aah.sftelehealthworker.repository

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.referHospital.HospitalResponse
import androidx.lifecycle.LiveData
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.DataResource
import com.aah.sftelehealthworker.models.home.AppointmentModel
import com.aah.sftelehealthworker.models.patient.PatientAppointment
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalListRepository {
    private val hospitalLiveData: MutableLiveData<DataResource<HospitalResponse>> =
        MutableLiveData<DataResource<HospitalResponse>>()
    var resourceLiveData: MutableLiveData<DataResource<HospitalResponse>> = hospitalLiveData

    fun getHospitalResponse(prescriptionId: String?) {

        val call: Call<HospitalResponse> = ApiClient.getApi().getHospitalListList(getToken(), prescriptionId)
            call.enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(call: Call<HospitalResponse>?, response: Response<HospitalResponse>?){
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as HospitalResponse
                    temp.status = response.code()
                    hospitalLiveData.postValue(DataResource.success(temp))
                } else {
                    if (response.body() == null) {
                        PatientRepository.appointmentModel.postValue(
                            DataResource.parseError(
                                response.errorBody()!!.string()
                            )
                        )
                    } else {
                        hospitalLiveData.postValue(DataResource.success(response.body()!!))
                    }
                }
            }

            override fun onFailure(call: Call<HospitalResponse>?, t: Throwable?) {
                val failed = AppointmentModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                hospitalLiveData.postValue(DataResource.error(failed.error!!))
            }
        })

    }

    private fun getToken(): String {
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance())
            .daoAccess().getToken()
    }
}