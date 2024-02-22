package com.perkantas.perpusptas_new.model

import java.io.Serializable

data class NotificationResponse(
    val success: Boolean,
    val message: String,
    val data: List<Data>
) {
    data class Data(
        val id: String,
        val data: NotificationData,
        val read_at: String?,
        val created_at: String,
    ) : Serializable {

        data class NotificationData(
            val title: String,
            val message: String,
            val type: String,
            val id: Int
        ) : Serializable
    }
}