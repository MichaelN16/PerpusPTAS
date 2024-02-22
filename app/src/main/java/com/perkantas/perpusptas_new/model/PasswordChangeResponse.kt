package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class PasswordChangeResponse(
    @SerializedName("success")
    var status: Boolean,

    @SerializedName("message")
    var messages: String
)
