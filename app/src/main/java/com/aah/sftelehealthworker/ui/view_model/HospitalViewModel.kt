package com.aah.sftelehealthworker.ui.view_model

import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.repository.HospitalListRepository
import androidx.lifecycle.LiveData
import com.aah.sftelehealthworker.models.referHospital.HospitalResponse

class HospitalViewModel : ViewModel() {
    var hospitalListRepository = HospitalListRepository()
    var hospitalResponse = hospitalListRepository.resourceLiveData!!
    fun getHospitalList(prescriptionId: String?) {
        hospitalListRepository.getHospitalResponse(prescriptionId)
    }
}