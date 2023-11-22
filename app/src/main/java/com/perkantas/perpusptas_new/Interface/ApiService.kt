package com.perkantas.perpusptas_new.Interface

import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.ModelBook
import com.perkantas.perpusptas_new.Auth.LoginRequest
import com.perkantas.perpusptas_new.Auth.LoginResponse
import com.perkantas.perpusptas_new.Auth.RegisterRequest
import com.perkantas.perpusptas_new.Auth.RegisterResponse
import com.perkantas.perpusptas_new.Model.PostsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //login
    @POST(Constants.LOGIN_URL)
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    //register
    @POST(Constants.REGISTER_URL)
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>


    @GET(Constants.POSTS_URL)
    fun fetchPosts(): Call<PostsResponse>

    //books
    @GET(Constants.BOOK_URL)
    fun getAllBooks(): Call<List<ModelBook>>

    /*//get user profile
    @GET(Constants.PROFILE_URL)
    fun getMyProfile(): Call<>

    //update user profile
    @POST(Constants.POSTS_URL)
    fun updateMyProfile(@Body request: ProfileRequest): Call<>*/

}