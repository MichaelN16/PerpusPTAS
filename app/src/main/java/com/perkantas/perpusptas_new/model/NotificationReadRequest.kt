package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

data class NotificationReadRequest(
    @SerializedName("id")
    var idNotification:String
)
