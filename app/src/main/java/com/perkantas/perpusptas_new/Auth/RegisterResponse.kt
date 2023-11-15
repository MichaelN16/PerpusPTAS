package com.perkantas.perpusptas_new.Auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success")
    var statusCode: Boolean,

    @SerializedName("message")
    var messages: String,

    @SerializedName("data")
    var dataReg: DataReg
)

data class DataReg (
    @SerializedName("name")
    var name: String,

    @SerializedName("token")
    var authToken: String
)
