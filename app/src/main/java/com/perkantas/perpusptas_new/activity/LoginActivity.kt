package com.perkantas.perpusptas_new.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.perkantas.perpusptas_new.model.LoginRequest
import com.perkantas.perpusptas_new.model.LoginResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var progressDialog: ProgressDialog

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

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

        //no account text button
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

        //forgot password button
        binding.forgotTv.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        //back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
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
        progressDialog.setMessage("Mencoba Login...")
        progressDialog.show()

        /*startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        progressDialog.dismiss()*/

        apiClient.getApiService(this).login(LoginRequest(email = email, password = password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@LoginActivity,"Gagal masuk akun karena ${t.message} ", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.statusCode == true && loginResponse.dataLog?.authToken != null) {
                            // Login success
                            sessionManager.saveDataLog(loginResponse.dataLog)
                            progressDialog.dismiss()
                            Toast.makeText(this@LoginActivity, "Berhasil Masuk akun, selamat datang " + loginResponse.dataLog.name, Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(this@LoginActivity, "Gagal masuk akun", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        //response failure untuk salah password/koneksi error/dll
                        progressDialog.dismiss()
                        Toast.makeText(this@LoginActivity, "Gagal masuk akun. Code: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}