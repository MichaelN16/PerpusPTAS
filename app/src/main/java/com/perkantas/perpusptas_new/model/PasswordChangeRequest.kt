package com.perkantas.perpusptas_new.model

import com.google.gson.annotations.SerializedName

data class PasswordChangeRequest(
    @SerializedName("email")
    var email: String,

    @SerializedName("old_password")
    var oldPassword: String,

    @SerializedName("new_password")
    var newPassword: String,

    @SerializedName("confirm_password")
    var cNewPassword: String
)
