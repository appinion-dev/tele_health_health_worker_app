package com.aah.sftelehealthworker.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import com.aah.sftelehealthworker.R
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun currentDate(currentDate: String): String? {
        val currentFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm a")
        try {
            val date = currentFormatter.parse(currentDate)
            val formatter2 = SimpleDateFormat(DATE_TIME_PATTERN_WORD)
            return formatter2.format(date)
        } catch (e: Exception) {
        }
        return null
    }

    fun currentDateFilter(currentDate: String): String? {
        val currentFormatter = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = currentFormatter.parse(currentDate)
            val formatter2 = SimpleDateFormat(DATE_PATTERN_WORD)
            return formatter2.format(date)
        } catch (e: Exception) {
        }
        return null
    }



    fun currentDate(): String? {
        val date = Date()
        val formatter = SimpleDateFormat(DATE_PATTERN_WORD)
        return formatter.format(date)
    }
}