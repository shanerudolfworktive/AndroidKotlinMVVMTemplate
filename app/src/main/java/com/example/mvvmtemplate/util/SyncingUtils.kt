package com.example.mvvmtemplate.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*


class SyncingUtils {
    companion object {
        fun calculateDiffFromNow(hour: Int, min: Int = 0, sec: Int = 0, ms: Int = 0): Long {
            val date = Date()
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, hour)// for 6 hour
            calendar.set(Calendar.MINUTE, min)// for 0 min
            calendar.set(Calendar.SECOND, sec)// for 0 sec
            if(calendar.time < date) calendar.add(Calendar.DATE, 1)
            return calendar.timeInMillis - date.time
        }
    }
}