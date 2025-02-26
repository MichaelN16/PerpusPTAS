package com.perkantas.perpusptas_new.retrofit

import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //login
    @POST(Constants.LOGIN_URL)
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    //register
    @POST(Constants.REGISTER_URL)
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    //load books list
    @GET(Constants.INDEX_URL)
    fun getBookIndex(@Query("category") category: String? = null): Call<BookResponse>

    // get user profile
    @GET(Constants.PROFILE_URL)
    fun getUserProfile(): Call<MyProfileResponse>

    //get category for viewPager tab
    @GET(Constants.CATEGORY_URL)
    fun getCategories(): Call<CategoryResponse>

    //update user info
    @POST(Constants.PROFILE_URL)
    fun updateUserProfile(
        @Body request: MyProfileRequest): Call<MyProfileResponse>

    //check user profile is filled or not
    @GET(Constants.PROFILE_CHECK)
    fun checkUserProfile(): Call<ProfileCheckResponse>

    @POST(Constants.RENT_URL)
    fun rentRequest(
        @Body request: RentRequest): Call<RentResponse>

    @GET(Constants.RENT_URL)
    fun getRentList(
        @Query("type") type: String
    ): Call<RentHistoryResponse> //query status

    @GET(Constants.NOTIFICATION_URL)
    fun getNotification(): Call<NotificationResponse>

    @POST(Constants.NOTIFICATION_URL_READ)
    fun sendReadMark(
        @Path("id") notificationId: String) : Call<NotificationResponse>

    @POST(Constants.PASSWORD_CHANGE_URL)
    fun passwordChange(
        @Body request: PasswordChangeRequest) : Call<PasswordChangeResponse>

    @FormUrlEncoded
    @POST(Constants.PASSWORD_RESET)
    fun passwordReset(
        @Field("email") email:String
    ) : Call<PasswordResetResponse>
}