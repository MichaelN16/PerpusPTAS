package com.perkantas.perpusptas_new.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatConverter(date: Date?, format: String): String {
    return date?.let {
        try {
            SimpleDateFormat(format, Locale.getDefault()).format(it)
        } catch (e: Exception) {
            e.printStackTrace()
            "Belum ada"
        }
    } ?: "Belum ada"
}

fun dateConverter(dateString: String?, inputFormat: String, outputFormat: String): String {
    dateString?.let {
        try {
            val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
            val date = inputFormatter.parse(it)
            return outputFormatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return "Belum ada"
}