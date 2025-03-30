package com.rakamin.newsapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertTime(date: String): String {
    val instant = Instant.parse(date)
    val zoneId = ZoneId.of("Asia/Jakarta")
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val formattedTime = instant.atZone(zoneId).format(formatter)
    return formattedTime
}