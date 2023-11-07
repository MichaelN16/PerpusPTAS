package com.perkantas.perpusptas_new.Model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    val books:ArrayList<Books>
)

data class Books (
    @SerializedName("id")
    val id: Int,

    @SerializedName("bookTitle")
    val bookTitle: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("bookCover")
    val bookCover: String,

    @SerializedName("bookDesc")
    val bookDesc: String,

    @SerializedName("stock")
    val stock: Int
)
