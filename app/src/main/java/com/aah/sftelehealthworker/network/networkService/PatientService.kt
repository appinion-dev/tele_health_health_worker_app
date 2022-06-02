package com.aah.sftelehealthworker.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.models.login.PhoneNo
import com.aah.sftelehealthworker.models.newPatient.*
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import com.aah.sftelehealthworker.utils.AppUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PatientService {
    @Synchronized
    fun requestPatientWithPhone(patientProfile: MutableLiveData<PatientProfile>, token :String, patientId :String): MutableLiveData<PatientProfile> {

        val call: Call<PatientProfile> = ApiClient.getApi().requestPatient(token, patientId)
        call.enqueue(object : Callback<PatientProfile> {
            override fun onResponse(call: Call<PatientProfile>?, response: Response<PatientProfile>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as PatientProfile
                    temp.status = response.code()
                    patientProfile.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = PatientProfile()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        patientProfile.postValue(temp)
                    }
                    else{
                        patientProfile.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<PatientProfile>?, t: Throwable?) {
                val failed = PatientProfile()
                failed.error = "Couldn't Connect"
                failed.status = 4
                patientProfile.postValue(failed)
            }
        })
        return patientProfile
    }

    @Synchronized
    fun requestVitals(vitalsModel: MutableLiveData<VitalsModel>, token :String, patientId :String): MutableLiveData<VitalsModel> {
        val call: Call<VitalsModel> = ApiClient.getApi().requestVitals(token, patientId)
        call.enqueue(object : Callback<VitalsModel> {
            override fun onResponse(call: Call<VitalsModel>?, response: Response<VitalsModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as VitalsModel
                    temp.status = response.code()
                    vitalsModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = VitalsModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        vitalsModel.postValue(temp)
                    }
                    else{
                        vitalsModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<VitalsModel>?, t: Throwable?) {
                val failed = VitalsModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                vitalsModel.postValue(failed)
            }
        })
        return vitalsModel
    }

    @Synchronized
    fun requestPrescription(prescriptionsModel: MutableLiveData<PrescriptionsModel>, token :String, patientId :String,caseId:String): MutableLiveData<PrescriptionsModel> {
        val call: Call<PrescriptionsModel> = ApiClient.getApi().requestPrescription(token, patientId,caseId)
        call.enqueue(object : Callback<PrescriptionsModel> {
            override fun onResponse(call: Call<PrescriptionsModel>?, response: Response<PrescriptionsModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as PrescriptionsModel
                    temp.status = response.code()
                    prescriptionsModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = PrescriptionsModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        prescriptionsModel.postValue(temp)
                    }
                    else{
                        prescriptionsModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<PrescriptionsModel>?, t: Throwable?) {
                val failed = PrescriptionsModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                prescriptionsModel.postValue(failed)
            }
        })
        return prescriptionsModel
    }

    @Synchronized
    fun requestReports(reportsModel: MutableLiveData<ReportsModel>, token :String, patientId :String): MutableLiveData<ReportsModel> {
        val call: Call<ReportsModel> = ApiClient.getApi().requestReports(token, patientId)
        call.enqueue(object : Callback<ReportsModel> {
            override fun onResponse(call: Call<ReportsModel>?, response: Response<ReportsModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as ReportsModel
                    temp.status = response.code()
                    reportsModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = ReportsModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        reportsModel.postValue(temp)
                    }
                    else{
                        reportsModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<ReportsModel>?, t: Throwable?) {
                val failed = ReportsModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                reportsModel.postValue(failed)
            }
        })
        return reportsModel
    }

    @Synchronized
    fun submitVitals(responseModel: MutableLiveData<ResponseModel>, token :String, vitalsSubmitModel : VitalsSubmitModel): MutableLiveData<ResponseModel> {
        val call: Call<ResponseModel> = ApiClient.getApi().submitVitals(token, vitalsSubmitModel)
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

    //RequestBody model, RequestBody modelProperty, RequestBody path, RequestBody patientId, RequestBody documentCategoryId, RequestBody title, RequestBody note, MultipartBody.Part image
    @Synchronized
    fun submitReports(responseModel: MutableLiveData<ResponseModel>, token :String, model : RequestBody, modelProperty:RequestBody , path:RequestBody , patientId:RequestBody , documentCategoryId:RequestBody , title:RequestBody , note:RequestBody , image: MultipartBody.Part): MutableLiveData<ResponseModel> {
        val call: Call<ResponseModel> = ApiClient.getApi().submitDocument(token, model, modelProperty, path, patientId, documentCategoryId, title, note, image)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {
                if (response!!.isSuccessful) {
                    if(response.code() == 201 || response.code() == 200) {
                        val temp = response.body() as ResponseModel
                        temp.status = response.code()
                        responseModel.postValue(temp)
                    }
                    else{
                        val temp = ResponseModel()
                        temp.error = response.message()
                        temp.status = response.code()
                        responseModel.postValue(temp)
                    }
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

}