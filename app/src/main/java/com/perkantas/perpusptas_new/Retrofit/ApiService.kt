package com.perkantas.perpusptas_new.Retrofit

import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //login
    @POST(Constants.LOGIN_URL)
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    //register
    @POST(Constants.REGISTER_URL)
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    //load books list (guest)
    @GET(Constants.INDEX_URL)
    fun getBookIndex(@Query("category") category: String? = null): Call<BookResponse>

    // get user profile
    @GET(Constants.PROFILE_URL)
    fun getUserProfile(@Header("Authorization") token: String): Call<MyProfileResponse>

    @GET(Constants.CATEGORY_URL)
    fun getCategories(): Call<CategoryResponse>


    //update user info
    @POST(Constants.PROFILE_URL)
    fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body request: MyProfileRequest): Call<MyProfileResponse>
}