package com.perkantas.perpusptas_new.Interface

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyProfileRequest(
    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("birth_place")
    var birthPlace: String,

    @SerializedName("birth_date")
    var birthDate: Date,

    @SerializedName("phone")
    var phone: Number,

    @SerializedName("address")
    var address: String,

    @SerializedName("photo")
    var photo: String,

    @SerializedName("component")
    var component: String
)
