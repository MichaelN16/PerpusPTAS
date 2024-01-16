package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName

data class ProfileCheckResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val messages: String,

    @SerializedName("data")
    val check: Check
) {
    data class Check(
        @SerializedName("boolean")
        val status: Boolean
    )
}
