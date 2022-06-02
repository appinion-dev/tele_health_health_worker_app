package com.aah.sftelehealthworker.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.DataResource
import com.aah.sftelehealthworker.models.home.AppointmentModel
import com.aah.sftelehealthworker.models.patient.PatientAppointment
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PatientRepository {

    var appointmentModel = MediatorLiveData<DataResource<PatientAppointment>>()

    @Synchronized
    fun requestPatientAppointmentList(
        token: String,
        patientId: String,
        status: String,
        date:String

        ) {
        appointmentModel.value = DataResource.loading()
        val call: Call<PatientAppointment> = ApiClient.getApi()
            .requestPatientAppointment(token, patientId, "1", "10000", status,date)
        call.enqueue(object : Callback<PatientAppointment> {
            override fun onResponse(
                call: Call<PatientAppointment>?,
                response: Response<PatientAppointment>?
            ) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as PatientAppointment
                    temp.status = response.code()
                    appointmentModel.postValue(DataResource.success(temp))
                } else {
                    if (response.body() == null) {
                        appointmentModel.postValue(
                            DataResource.parseError(
                                response.errorBody()!!.string()
                            )
                        )
                    } else {
                        appointmentModel.postValue(DataResource.success(response.body()!!))
                    }
                }
            }

            override fun onFailure(call: Call<PatientAppointment>?, t: Throwable?) {
                val failed = AppointmentModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                appointmentModel.postValue(DataResource.error(failed.error!!))
            }
        })

    }
}