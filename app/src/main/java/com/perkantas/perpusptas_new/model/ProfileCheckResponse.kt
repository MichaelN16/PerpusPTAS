package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class ProfileCheckResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val messages: String,

    @SerializedName("data")
    val status: Boolean
)
