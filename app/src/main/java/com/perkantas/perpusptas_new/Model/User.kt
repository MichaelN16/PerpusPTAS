package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    /*datanya id, email, nama*/

    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("email")
    @Expose
    var email: String
/*
    @SerializedName("birth_place")
    @Expose
    var birthPlace: String,

    @SerializedName("birth_date")
    @Expose
    var birthDate: String,

    @SerializedName("phone")
    @Expose
    var phone: String,

    @SerializedName("address")
    @Expose
    var address: String,

    @SerializedName("photo")
    @Expose
    var photo: String,

    @SerializedName("component")
    @Expose
    var component: String*/
)
