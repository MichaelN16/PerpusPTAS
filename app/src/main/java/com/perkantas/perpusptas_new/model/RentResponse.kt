package com.perkantas.perpusptas_new.model

data class RentResponse(
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
        val status: String?,
        val book: ArrayList<Book>
    )
    {
        data class Book(
            val id: Int,
            val book_code: String,
            val book_title: String,
            val author: String,
            val category: Int,
            val book_cover: String
        )
    }
}
