package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyProfileRequest(
    @SerializedName("name")
    var name: String,

    @SerializedName("birth_place")
    var birthPlace: String,

    @SerializedName("birth_date")
    var birthDate: Date,

    @SerializedName("phone")
    var phone: Long,

    @SerializedName("address")
    var address: String,

    @SerializedName("component")
    var component: String
)

