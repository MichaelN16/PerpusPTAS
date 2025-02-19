package com.perkantas.perpusptas_new.auth

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.perkantas.perpusptas_new.model.LoginResponse
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.model.MyProfileResponse

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object{
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_DATA = "user"
    }

    fun saveDataLog(data: LoginResponse.DataLog){
        val editor = prefs.edit()
        editor.putString(USER_NAME, data.name)
        editor.putString(USER_TOKEN, data.authToken)
        editor.apply()
    }

    fun saveUserData(value: MyProfileResponse.DataProf){
        val editor = prefs.edit()
        val json = Gson().toJson(value)
        editor.putString(USER_DATA, json)
        editor.apply()
    }

    //func to save auth token
    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    //func to fetch auth token
    fun fetchAuthToken(): String?{
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchDataLog(): LoginResponse.DataLog{
        if (prefs.getString(USER_TOKEN, null)==null){
            return LoginResponse.DataLog("","")
        }
        return LoginResponse.DataLog(
            prefs.getString(USER_TOKEN, null)!!,
            prefs.getString(USER_NAME, null)!!
        )
    }

    fun fetchUserData(): MyProfileResponse.DataProf? {
        val json = prefs.getString(USER_DATA, null)
        return try {
            Gson().fromJson(json, MyProfileResponse.DataProf::class.java)
        }  catch (e: Exception) {
            null
        }
    }

    fun clearUserData(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    //func to delete auth token
    fun deleteAuthToken(){
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return fetchAuthToken() != null
    }
}