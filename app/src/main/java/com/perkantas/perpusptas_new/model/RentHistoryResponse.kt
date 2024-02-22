package com.perkantas.perpusptas_new.model

import java.io.Serializable

data class RentHistoryResponse(
    val success: Boolean,
    val message: String,
    val data: ArrayList<RentData>
) {
    data class RentData(
    val id: Int,
    val books_id: Int,
    val users_id: Int,
    val date_request: String,
    val date_rent: String?,
    val date_due: String?,
    val date_return: String?,
    val status: Int?,
    val book: Book
) : Serializable
    {
        data class Book(
            val id: Int,
            val book_code: String,
            val book_title: String,
            val category: String,
            val book_cover: String,
            //progress bar to load book cover
            var isLoading: Boolean = false
        ) : Serializable
    }
}