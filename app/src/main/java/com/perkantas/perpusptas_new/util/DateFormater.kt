package com.perkantas.perpusptas_new.util

import com.perkantas.perpusptas_new.model.RentHistoryResponse
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
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

fun getIntervalFromTimestamp(timestamp:String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = dateFormat.parse(timestamp)
    val timestampMilliseconds = date?.time ?: 0

    val currentInstant = Instant.now()
    val timestampInstant = Instant.ofEpochMilli(timestampMilliseconds)

    val currentDateTime = LocalDateTime.ofInstant(currentInstant, ZoneId.systemDefault())
    val timestampDateTime =
        LocalDateTime.ofInstant(timestampInstant, ZoneId.systemDefault())

    val diffInSeconds = java.time.Duration.between(timestampDateTime, currentDateTime).seconds

    return when {
        diffInSeconds < 60 -> "Baru saja"
        diffInSeconds < 3600 -> "${diffInSeconds / 60} menit yang lalu"
        diffInSeconds < 86400 -> "${diffInSeconds / 3600} jam yang lalu"
        else -> "${diffInSeconds / 86400} hari yang lalu"
    }
}

fun getStatus(rentedBook: RentHistoryResponse.RentData) : CharSequence? {
    val currentDate = LocalDate.now().toString()
    val formattedDate = dateConverter(currentDate, "yyyy-MM-dd", "dd/MM/yyyy")
    return when {
        rentedBook.date_due.isNullOrEmpty() -> "Menunggu"
        rentedBook.date_return.isNullOrEmpty() -> "Meminjam"
        formattedDate >= rentedBook.date_due -> "Terlambat"
        else -> "Selesai"
    }
}