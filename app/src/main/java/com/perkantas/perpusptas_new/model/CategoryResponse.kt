package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("success")
    val statusCode: Boolean,

    @SerializedName("message")
    val messages: String,

    @SerializedName("data")
    val dataCat: ArrayList<DataCat>
){
    data class DataCat(
        @SerializedName("id")
        var categoryId: Int,

        @SerializedName("name")
        var category: String
    )
}