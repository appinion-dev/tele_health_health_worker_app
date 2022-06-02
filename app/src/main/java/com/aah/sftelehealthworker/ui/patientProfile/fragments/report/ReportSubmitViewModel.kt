package com.aah.sftelehealthworker.ui.patientProfile.fragments.report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.network.networkService.PatientService
import com.aah.sftelehealthworker.utils.ProgressRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ReportSubmitViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun setSuccessFullFalse(){
        isSuccessFull.value = false
    }

    fun submitData(
        imageFile: File,
        filename: String,
        documentNote: String,
        id: Int,
        status: String
    ) {

//        var filenameTemp = "$filename.$status"
        var filenameTemp = ""

        var modelProperty = if (status.toLowerCase() == "pdf") {
            filenameTemp = "$filename.pdf"
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "pdf")
        } else {
            filenameTemp = "$filename.jpg"
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "image")
        }

        val model = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "PatientDocument")
        val path = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "PATIENT_DOC_PATH")

        val patientId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), id.toString())
        val documentCategoryId = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "2")
        val title = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), filenameTemp)
        val note = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), documentNote)

        // create RequestBody instance from file
        //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), documentImage);
        val requestFile = ProgressRequestBody(
            imageFile,
            object : ProgressRequestBody.UploadCallbacks {
                override fun onProgressUpdate(percentage: Int) {

                }

                override fun onFinish() {

                }

            })

        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", filenameTemp, requestFile)

        PatientService.submitReports(
            MutableLiveData(),
            getToken(),
            model,
            modelProperty,
            path,
            patientId,
            documentCategoryId,
            title,
            note,
            image
        ).observeForever{
            if (it.status == 201 || it.status == 200){
                message.value = "Successful"
                isSuccessFull.value = true
            }
            else{
                message.value = it.error
            }

        }
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}