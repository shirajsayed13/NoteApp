package com.task.noteapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(timeInMs: Long): String {
        val date = Date(timeInMs)
        val simpleDateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy")
        return simpleDateFormat.format(date)
    }


}