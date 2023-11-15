package com.perkantas.perpusptas_new

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.perkantas.perpusptas_new.Auth.LoginRequest
import com.perkantas.perpusptas_new.Auth.LoginResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var progressDialog: ProgressDialog

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    val Any.TAG: String
    get(){
        val tag = javaClass.simpleName
        return if (tag.length <=23) tag else tag.substring(0, 23)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        //init progress dialog, will show while login User
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClickListener {
            /*Steps
            * 1. Input Data
            * 2. Validate Data
            * 3. Login*/
            validateData()
        }
        binding.forgotTv.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
    private var email = ""
    private var password = ""

    private fun validateData() {
        //1. Input Data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //2. Validate Data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()){
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show()
        } else{
            loginUser()
        }
    }

    private fun loginUser() {
        apiClient.getApiService(this).login(LoginRequest(email = email, password = password))
            .enqueue(object : Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,"Gagal masuk akun karena ${t.message} ", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()

                    if (loginResponse?.statusCode == true && loginResponse.dataLog.authToken != null){
                        //coba myprofile
                        Log.e(TAG, "token: " + loginResponse.dataLog.authToken) //login token nanti diakses ke myprofile untuk akses data user
                        sessionManager.saveAuthToken(loginResponse.dataLog.authToken)
                        Toast.makeText(this@LoginActivity,"Berhasil Masuk akun", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    }
                    else{
                        Log.e(TAG, "onResponse: " + response.body())
                        Toast.makeText(this@LoginActivity,"Gagal masuk akun", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

}