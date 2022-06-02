package com.aah.sftelehealthworker.network.networkService

import androidx.lifecycle.MutableLiveData
import com.aah.sftelehealthworker.models.ResponseModel
import com.aah.sftelehealthworker.models.home.PatientProfileModel
import com.aah.sftelehealthworker.models.login.OtpModel
import com.aah.sftelehealthworker.models.login.PhoneNo
import com.aah.sftelehealthworker.models.newPatient.DistrictUpazillasModel
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.network.retrofit.ApiClient
import com.aah.sftelehealthworker.utils.AppUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object VerifyPatientService {
    @Synchronized
    fun requestPatientWithPhone(otpModel: MutableLiveData<OtpModel>, phoneNo :String): MutableLiveData<OtpModel> {
        val phone = PhoneNo()
        phone.phone = phoneNo.toString()
        val call: Call<OtpModel> = ApiClient.getApi().requestPatientWithPhone(phone)
        call.enqueue(object : Callback<OtpModel> {
            override fun onResponse(call: Call<OtpModel>?, response: Response<OtpModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as OtpModel
                    temp.status = response.code()
                    otpModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = OtpModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())
                        temp.error = response.error
                        temp.status = response.status
                        otpModel.postValue(temp)
                    }
                    else{
                        otpModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<OtpModel>?, t: Throwable?) {
                val failed = OtpModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                otpModel.postValue(failed)
            }
        })
        return otpModel;
    }

    @Synchronized
    fun requestPatientWithOtp(responseModel: MutableLiveData<ResponseModel>,token :String, phoneNo :String, otp :String): MutableLiveData<ResponseModel> {
        val phone = PhoneNo()
        phone.phone = phoneNo
        phone.otp = otp
        val call: Call<ResponseModel> = ApiClient.getApi().requestPatientWithOtp(token, phone)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
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
        return responseModel;
    }

    @Synchronized
    fun requestDistrictUpazillas(districtUpazillasModel: MutableLiveData<DistrictUpazillasModel>): MutableLiveData<DistrictUpazillasModel> {
        val call: Call<DistrictUpazillasModel> = ApiClient.getApi().requestDistrictUpazillas()
        call.enqueue(object : Callback<DistrictUpazillasModel> {
            override fun onResponse(call: Call<DistrictUpazillasModel>?, response: Response<DistrictUpazillasModel>?) {
                if (response!!.isSuccessful && response.code() == 200) {
                    val temp = response.body() as DistrictUpazillasModel
                    temp.status = response.code()
                    districtUpazillasModel.postValue(temp)
                }
                else{
                    if(response.body() == null) {
                        val temp = DistrictUpazillasModel()
                        val response = AppUtils.parseErrorResponse(response.errorBody()!!.string())

                        temp.error = response.error
                        temp.status = response.status
                        districtUpazillasModel.postValue(temp)
                    }
                    else{
                        districtUpazillasModel.postValue(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<DistrictUpazillasModel>?, t: Throwable?) {
                val failed = DistrictUpazillasModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                districtUpazillasModel.postValue(failed)
            }
        })
        return districtUpazillasModel;
    }

    @Synchronized
    fun requestCreatePatient(patientProfileModel: MutableLiveData<PatientProfileModel>, token :String, patientProfile :PatientProfile): MutableLiveData<PatientProfileModel> {
        val call: Call<PatientProfileModel> = ApiClient.getApi().requestCreatePatient(token, patientProfile)
        call.enqueue(object : Callback<PatientProfileModel> {
            override fun onResponse(call: Call<PatientProfileModel>?, response: Response<PatientProfileModel>?) {
                if (response!!.isSuccessful && response.code() == 201) {
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
    fun requestCreatePatientImage(responseModel: MutableLiveData<ResponseModel>, token :String, id : RequestBody, modelProperty : RequestBody, path : RequestBody, model : RequestBody, image: MultipartBody.Part): MutableLiveData<ResponseModel> {
        val call: Call<ResponseModel> = ApiClient.getApi().requestCreatePatientImage(token, id, modelProperty, path, model, image)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {
                if (response!!.isSuccessful) {
                    if(response.code() == 201 || response.code() == 200 ) {
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
                val failed = PatientProfileModel()
                failed.error = "Couldn't Connect"
                failed.status = 4
                responseModel.postValue(failed)
            }
        })
        return responseModel
    }

}