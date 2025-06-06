package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyProfileResponse(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var dataProf: DataProf
) {
    data class DataProf(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("birth_place")
        var birthPlace: String,

        @SerializedName("birth_date")
        var birthDate: Date,

        @SerializedName("phone")
        var phone: String,

        @SerializedName("address")
        var address: String,

        @SerializedName("photo")
        var photo: String,

        @SerializedName("component")
        var component: String
    )
}
