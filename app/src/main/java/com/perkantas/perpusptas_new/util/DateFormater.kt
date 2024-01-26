package com.perkantas.perpusptas_new.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatConverter(date: Date, targetFormat: String): String{
    val targetDateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())
    return targetDateFormat.format(date)
}