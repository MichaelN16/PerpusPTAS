package com.perkantas.perpusptas_new.auth

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response{
        val requestBuilder = chain.request().newBuilder()

        //if token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let{
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}