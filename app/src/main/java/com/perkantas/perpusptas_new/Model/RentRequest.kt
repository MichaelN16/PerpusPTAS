package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName

data class RentRequest(
    @SerializedName("date_request")
    var dateReq: String
)
