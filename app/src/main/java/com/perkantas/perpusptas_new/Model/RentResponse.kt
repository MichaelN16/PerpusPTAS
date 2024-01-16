package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName

data class RentResponse(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var dataRent: ArrayList<DataRent>
) {
    data class DataRent(
        @SerializedName("books_id")
        var bookId: Int,

        @SerializedName("users_id")
        var userId: Int,

        @SerializedName("date_request")
        var dateReq: String,

        @SerializedName("date_due")
        var dateDue: String,

        @SerializedName("date_return")
        var dateReturn: String,

        @SerializedName("status")
        var status: String
    )
}
