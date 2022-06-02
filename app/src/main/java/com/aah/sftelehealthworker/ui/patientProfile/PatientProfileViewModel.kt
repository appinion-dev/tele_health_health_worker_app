package com.aah.sftelehealthworker.ui.patientProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.home.PatientProfileModel
import com.aah.sftelehealthworker.models.newPatient.District
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.network.networkService.PatientService
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService
import com.aah.sftelehealthworker.utils.ProgressRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PatientProfileViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val patientProfileMutableLiveData = MutableLiveData<PatientProfile>()

    fun loadData(patientId: String) {

        PatientService.requestPatientWithPhone(MutableLiveData(),getToken(), patientId).observeForever { patientProfile ->
            if (patientProfile.status == 200) {
                patientProfileMutableLiveData.value = patientProfile
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = patientProfile.error
            }
            refresh.value = false
        }
    }

    fun uploadImage(patientId: String, file: File){

//        var filenameTemp = "${file.name}.jpg"

        val id = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), patientId)
        val modelProperty = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "image")
        val path = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "PATIENT_IMAGE_PATH")
        val model = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "Patient")

        val requestFile = ProgressRequestBody(
            file,
            object : ProgressRequestBody.UploadCallbacks {
                override fun onProgressUpdate(percentage: Int) {

                }

                override fun onFinish() {

                }

            })

        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)


        VerifyPatientService.requestCreatePatientImage(MutableLiveData(), getToken() , id, modelProperty, path, model, image).observeForever { responseModel ->
            if ( responseModel.status == 201 || responseModel.status == 200) {
//                patientProfileMutableLiveData.value = responseModel
                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = responseModel.error
            }
            refresh.value = false
        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}