package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("success")
    var statusCode: Boolean,

    @SerializedName("message")
    var messages: String,

    @SerializedName("data")
    var dataCat:DataCat
){
    data class DataCat(
        @SerializedName("id")
        var categoryId: Int,

        @SerializedName("name")
        var category: String
    )
}