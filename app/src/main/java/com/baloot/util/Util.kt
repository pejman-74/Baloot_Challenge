package com.baloot.util

import java.text.SimpleDateFormat
import java.util.*

object Util {


    fun String.toTime(
        inputPattern: String = "yyyy-MM-dd'T'HH:mm:ss",
        outputPatten: String = "yyyy-MM-dd"
    ): String? {
        return runCatching {
            var simpleDataFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val date = simpleDataFormat.parse(this)
            date ?: return null
            simpleDataFormat = SimpleDateFormat(outputPatten, Locale.getDefault())
            simpleDataFormat.format(date)
        }.getOrNull()
    }




}