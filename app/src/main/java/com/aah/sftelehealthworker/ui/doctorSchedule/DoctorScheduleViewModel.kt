package com.aah.sftelehealthworker.ui.doctorSchedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aah.sftelehealthworker.application.MyApplication
import com.aah.sftelehealthworker.database.AppDatabase
import com.aah.sftelehealthworker.models.appoinment.TimeSlot
import com.aah.sftelehealthworker.network.networkService.AppointmentService

class DoctorScheduleViewModel : ViewModel() {
    val isSuccessFull = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val timeSlotMutableLiveData = MutableLiveData<List<TimeSlot>>()

    fun loadData( doctorName : String,doctorId : String ,categoryId : String, time : String) {
        refresh.value = true
        AppointmentService.requestTimeSlots(MutableLiveData(), getToken(), doctorId, categoryId, time).observeForever { timeSlotsModel ->
            if (timeSlotsModel.status == 200) {
//                if(timeSlotsModel.timeSlots.isNullOrEmpty()){
//                    message.value = "Empty"
//                }
//                else{
                    timeSlotMutableLiveData.value = getTimeSlote(timeSlotsModel.timeSlots!!, doctorName)
//                }
//                isSuccessFull.value = true
            }
            else {
                isSuccessFull.value = false
                message.value = timeSlotsModel.error
            }
            refresh.value = false
        }
    }

    private fun getTimeSlote(timeSlots: List<TimeSlot>, doctorName: String): List<TimeSlot> {

        val timeSlotsList = mutableListOf<TimeSlot>()

        timeSlots.forEach {
            val timeSlot = TimeSlot()
            timeSlot.callbackRate = it.callbackRate
            timeSlot.doctorName = doctorName
            timeSlot.endTime = it.endTime
            timeSlot.followUpRate = it.followUpRate
            timeSlot.startTime = it.startTime
            timeSlot.timeslotId = it.timeslotId
            timeSlot.timezone = it.timezone
            timeSlot.weekday = it.weekday

            timeSlotsList.add(timeSlot)
        }

        return timeSlotsList
    }

    private fun getToken():String{
        return "Bearer " + AppDatabase.getAppDatabase(MyApplication.getMyApplicationInstance()).daoAccess().getToken()
    }
}