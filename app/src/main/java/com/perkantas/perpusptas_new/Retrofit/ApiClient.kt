package com.perkantas.perpusptas_new.Retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.perkantas.perpusptas_new.AuthInterceptor
import com.perkantas.perpusptas_new.Constants
import com.perkantas.perpusptas_new.Interface.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(context: Context): ApiService{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
        //Initialize ApiService if not initialized yet
        if(!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) //add our okhttp client
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context)) //if error, undo this
            .build()
    }

}