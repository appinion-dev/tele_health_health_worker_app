package com.aah.sftelehealthworker.ui.patientProfileCreate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.home.PatientProfileModel
import com.aah.sftelehealthworker.models.newPatient.District
import com.aah.sftelehealthworker.models.newPatient.PatientProfile
import com.aah.sftelehealthworker.network.networkService.VerifyPatientService
import com.aah.sftelehealthworker.utils.ProgressRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PatientProfileCreateViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val isImageUploadSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val districtMutableLiveData = MutableLiveData<List<District>>()
    val patientProfileMutableLiveData = MutableLiveData<PatientProfileModel>()

    fun setSuccessFullFalse() {
        isSuccessFull.value = false
    }

    fun setImageUploadSuccessFull() {
        isImageUploadSuccessFull.value = false
    }


    fun loadData(phone: String, name: String, age: String, gender: String, upazillaId: String) {
        refresh.value = true
        val patientProfile = PatientProfile()
        patientProfile.phone = phone
        patientProfile.name = name
        patientProfile.age = age
        patientProfile.gender = gender
        patientProfile.upazillaId = upazillaId.toInt()
        patientProfile.height = 0
        patientProfile.weight = 0
        patientProfile.bloodGroup = ""

        VerifyPatientService.requestCreatePatient(MutableLiveData(), getToken(), patientProfile)
            .observeForever { patientProfileModel ->
                if (patientProfileModel.status == 201) {
                    patientProfileMutableLiveData.value = patientProfileModel
                    isSuccessFull.value = true
                } else {
                    isSuccessFull.value = false
                    message.value = patientProfileModel.error
                }
                refresh.value = false
            }
    }

    fun loadData(phone: String, name: String, age: String, gender: String, upazillaId: String, file: File) {
//        fun loadData(
//            phone: String,
//            name: String,
//            age: String,
//            gender: String,
//            upazillaId: String
//        ) {
        refresh.value = true
        val patientProfile = PatientProfile()
        patientProfile.phone = phone
        patientProfile.name = name
        patientProfile.age = age
        patientProfile.gender = gender
        patientProfile.upazillaId = upazillaId.toInt()
        patientProfile.height = 0
        patientProfile.weight = 0
        patientProfile.bloodGroup = ""

        VerifyPatientService.requestCreatePatient(MutableLiveData(), getToken(), patientProfile)
            .observeForever { patientProfileModel ->
                if (patientProfileModel.status == 201) {
                    patientProfileMutableLiveData.value = patientProfileModel
//                isSuccessFull.value = true
                    if (file != null) {
                        uploadImage(patientProfileModel, file)
                    } else {
                        isSuccessFull.value = true
                    }
                } else {
                    isSuccessFull.value = false
                    message.value = patientProfileModel.error
                }
                refresh.value = false
            }
    }

    private fun uploadImage(patientProfileModel: PatientProfileModel, file: File) {

        var filenameTemp = "${file.name}.jpg"

        val id = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            patientProfileModel.patientProfile?.id.toString()
        )
        val modelProperty = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "image")
        val path =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "PATIENT_IMAGE_PATH")
        val model = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "Patient")

//        val file = File(uri.path)
//        val compressedImageFile = Compressor.compress(MyApplication.getMyApplicationInstance(), )
//        val imageFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//
//        val imageFilePart = MultipartBody.Part.createFormData("image", filenameTemp, imageFile)

        val requestFile = ProgressRequestBody(
            file,
            object : ProgressRequestBody.UploadCallbacks {
                override fun onProgressUpdate(percentage: Int) {

                }

                override fun onFinish() {

                }

            })

        val image: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", filenameTemp, requestFile)


        VerifyPatientService.requestCreatePatientImage(
            MutableLiveData(),
            getToken(),
            id,
            modelProperty,
            path,
            model,
            image
        ).observeForever { responseModel ->
            if (responseModel.status == 201 || responseModel.status == 200) {
//                patientProfileMutableLiveData.value = responseModel
                isSuccessFull.value = true
            } else {
                isSuccessFull.value = false
                message.value = responseModel.error
            }
            refresh.value = false
        }
    }

    fun loadDistrictUpazillas() {
        refresh.value = true

        VerifyPatientService.requestDistrictUpazillas(MutableLiveData())
            .observeForever { districtUpazillasModel ->
                if (districtUpazillasModel.status == 200) {
//                otpLiveData.value = otpModel
                    districtMutableLiveData.value = districtUpazillasModel.districts
//                isSuccessFull.value = true
                } else {
//                isSuccessFull.value = false
                    message.value = districtUpazillasModel.error
                }
                refresh.value = false
            }
    }

    private fun getToken(): String {
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance())
            .daoAccess().getToken()
    }

//    "phone": "1676470867",
//    "name": "Hasina Begum",
//    "dob": "1995-04-05",
//    "gender": "Female",
//    "upazillaId": 1,
//    "height": 184.5,
//    "weight": 70.5,
//    "bloodGroup": "A-"
}