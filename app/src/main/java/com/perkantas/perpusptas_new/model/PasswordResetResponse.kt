package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class PasswordResetResponse(
    @SerializedName("message")
    var messages: String
)

