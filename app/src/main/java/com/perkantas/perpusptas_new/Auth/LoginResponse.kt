package com.perkantas.perpusptas_new.Auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    var statusCode: Boolean,

    @SerializedName("message")
    var messages: String,

    @SerializedName("data")
    var dataLog: DataLog
) {
    data class DataLog(
        @SerializedName("token")
        var authToken: String,

        @SerializedName("name")
        var name: String
    )
}


