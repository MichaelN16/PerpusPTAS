package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class RentRequest(
    @SerializedName("books_id")
    var bookId: Int
)
