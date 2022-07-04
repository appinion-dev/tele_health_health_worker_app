package com.aah.sftelehealthworker.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.appoinment.*
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import com.aah.sftelehealthworker.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AppointmentService {
    @Synchronized
    fun requestCategories(categoriesModel: MutableLiveData<CategoriesModel>, token :String): MutableLiveData<CategoriesModel> {

        val call: Call<CategoriesModel> = ApiClient.getApi().requestCategories(token)
        call.enqueue(object : Callback<CategoriesModel> {
            override fun onResponse(call: Call<CategoriesModel>?, response: Response<CategoriesModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as CategoriesModel
                    temp.status = response.code()
                    categoriesModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = CategoriesModel()
                        temp.error = response.message()
                        temp.status = response.code()
                        categoriesModel.postValue(temp)
                    }
                    else{
                        categoriesModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<CategoriesModel>?, t: Throwable?) {
                val failed = CategoriesModel()
                failed.error = "Couldn't Connect,Something went wrong"
                failed.status = 4
                categoriesModel.postValue(failed)
            }
        })
        return categoriesModel
    }

    @Synchronized
    fun requestDoctorList(doctorsModel: MutableLiveData<DoctorsModel>, token :String, categoryId : String, pageNo:String, size: String,branchID:String): MutableLiveData<DoctorsModel> {

        val call: Call<DoctorsModel> = ApiClient.getApi().requestDoctorList(token, categoryId,branchID, pageNo, size)
        call.enqueue(object : Callback<DoctorsModel> {
            override fun onResponse(call: Call<DoctorsModel>?, response: Response<DoctorsModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as DoctorsModel
                    temp.status = response.code()
                    doctorsModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = DoctorsModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        doctorsModel.postValue(temp)
                    }
                    else{
                        doctorsModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<DoctorsModel>?, t: Throwable?) {
                val failed = DoctorsModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                doctorsModel.postValue(failed)
            }
        })
        return doctorsModel
    }

    @Synchronized
    fun requestTimeSlots(timeSlotsModel: MutableLiveData<TimeSlotsModel>, token :String, doctorId : String,categoryId : String, time:String): MutableLiveData<TimeSlotsModel> {

        val call: Call<TimeSlotsModel> = ApiClient.getApi().requestTimeSlots(token, doctorId, categoryId, time)
        call.enqueue(object : Callback<TimeSlotsModel> {
            override fun onResponse(call: Call<TimeSlotsModel>?, response: Response<TimeSlotsModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as TimeSlotsModel
                    temp.status = response.code()
                    timeSlotsModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = TimeSlotsModel()

                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())

                        temp.error = response.error
                        temp.status = response.status
                        timeSlotsModel.postValue(temp)
                    }
                    else{
                        timeSlotsModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<TimeSlotsModel>?, t: Throwable?) {
                val failed = TimeSlotsModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                timeSlotsModel.postValue(failed)
            }
        })
        return timeSlotsModel
    }


    @Synchronized
    fun requestCreateAppointment(responseModel: MutableLiveData<ResponseModel>, token :String, createAppointmentModel: CreateAppointmentModel): MutableLiveData<ResponseModel> {

        val call: Call<ResponseModel> = ApiClient.getApi().requestCreateAppointment(token, createAppointmentModel)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {
                if (response!!.isSuccessful && response.code() == 201) {
                    val temp = response.body() as ResponseModel
                    temp.status = response.code()
                    responseModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = ResponseModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        responseModel.postValue(temp)
                    }
                    else{
                        responseModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<ResponseModel>?, t: Throwable?) {
                val failed = ResponseModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                responseModel.postValue(failed)
            }
        })
        return responseModel
    }

    @Synchronized
    fun requestDoctorPatientDetails(doctorsInfoModel: MutableLiveData<DoctorsInfoModel>, token :String, doctorId: String, timeslotId:String): MutableLiveData<DoctorsInfoModel> {

        val call: Call<DoctorsInfoModel> = ApiClient.getApi().requestDoctorPatientDetails(token, doctorId, timeslotId)
        call.enqueue(object : Callback<DoctorsInfoModel> {
            override fun onResponse(call: Call<DoctorsInfoModel>?, response: Response<DoctorsInfoModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as DoctorsInfoModel
                    temp.status = response.code()
                    doctorsInfoModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = DoctorsInfoModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        doctorsInfoModel.postValue(temp)
                    }
                    else{
                        doctorsInfoModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<DoctorsInfoModel>?, t: Throwable?) {
                val failed = DoctorsInfoModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                doctorsInfoModel.postValue(failed)
            }
        })
        return doctorsInfoModel
    }
}