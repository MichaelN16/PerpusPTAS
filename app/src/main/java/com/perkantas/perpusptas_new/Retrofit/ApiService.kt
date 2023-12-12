package com.perkantas.perpusptas_new.Retrofit

import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Model.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {
    //login
    @POST(Constants.LOGIN_URL)
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    //register
    @POST(Constants.REGISTER_URL)
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    //load books list
    @GET(Constants.BOOK_URL)
    fun getBooks(): Call<BookResponse>

    //load books list (guest)
    @GET(Constants.INDEX_URL)
    fun getBookIndex(): Call<BookResponse>

    //get categories
    @GET(Constants.CATEGORIES_URL)
    fun getCategories():Call<CategoryResponse>

    // get user profile
    @GET(Constants.PROFILE_URL)
    fun getUserProfile(@Header("Authorization") token: String): Call<MyProfileResponse>

    //update user info
    @POST(Constants.PROFILE_URL)
    fun updateUserProfile(
        @Header("Authorization") token: String,
        @Field("name") name:String,
        @Field("birth_place") birthPlace: String,
        @Field("birth_date") birthDate: Date,
        @Field("phone") phone:Long,
        @Field("address") address:String,
        @Field("component") component:String
    ): Call<MyProfileResponse>

}