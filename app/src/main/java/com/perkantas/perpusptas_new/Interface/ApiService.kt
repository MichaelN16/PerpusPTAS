package com.perkantas.perpusptas_new.Interface

import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.Book
import com.perkantas.perpusptas_new.Model.LoginRequest
import com.perkantas.perpusptas_new.Model.LoginResponse
import com.perkantas.perpusptas_new.Model.PostsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST(Constants.LOGIN_URL)
    //@FormUrlEncoded
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    @GET(Constants.POSTS_URL)
    fun fetchPosts(): Call<PostsResponse>

    //books
    @GET(Constants.BOOK_URL)
    fun getAllBooks(): Call<List<Book>>
}