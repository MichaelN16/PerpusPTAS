package com.perkantas.perpusptas_new.Interface

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val dataBook: ArrayList<DataBook>
) {
    data class DataBook (
        @SerializedName("id")
        var id: Int,

        @SerializedName("book_code")
        var bookCode: String,

        @SerializedName("book_title")
        var bookTitle: String,

        @SerializedName("author")
        var author: String,

        @SerializedName("category")
        var category: String,

        @SerializedName("publisher")
        var publisher: String,

        @SerializedName("stock")
        var stock: Int,

        @SerializedName("book_cover")
        var bookCover: String,

        @SerializedName("book_desc")
        var bookDesc: String,

        @SerializedName("barcode")
        var barcode: String
    )
}


