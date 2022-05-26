package com.ich.reciptopia.common.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDatetimeString(): String{
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ss")

        return now.format(formatter)
    }
}