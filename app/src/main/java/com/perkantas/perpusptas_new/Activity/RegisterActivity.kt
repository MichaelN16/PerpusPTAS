package com.perkantas.perpusptas_new.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.perkantas.perpusptas_new.Auth.RegisterRequest
import com.perkantas.perpusptas_new.Auth.RegisterResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

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
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        //handle click, back btn
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click, submit registration btn
        binding.registerBtn.setOnClickListener {
            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""
    private var cPassword = ""

    private fun validateData() {
        //Input, gather all user information data
        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        cPassword = binding.cPasswordEt.text.toString().trim()

        //Validate Data, to prevent empty or unvalidate data
        if (name.isEmpty()) {
            Toast.makeText(this, "Masukkan nama Anda", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show()
        } else if (cPassword.isEmpty()) {
            Toast.makeText(this, "Masukkan lagi password Anda", Toast.LENGTH_SHORT).show()
        } else if (password != cPassword){
            Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show()
        } else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Membuat Akun...")
        progressDialog.show()
        val registerRequest = RegisterRequest(name, email, password,cPassword)

        /*startActivity(Intent(this, LoginActivity::class.java))
        progressDialog.dismiss()*/

        apiClient.getApiService(this).register(registerRequest)
            .enqueue(object : Callback<RegisterResponse>{
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@RegisterActivity,"Gagal mendaftarkan karena ${t.message} ", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    val registerResponse = response.body()

                    if (registerResponse?.statusCode==true && registerResponse.dataReg.authToken !=null){
                        sessionManager.saveAuthToken(registerResponse.dataReg.authToken)
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterActivity, "Berhasil membuat akun, silahkan Login", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                    else{
                        //response untuk email sudah dipakai/password kurang sesuai aturan/dll
                        Log.d(TAG, "Login failed. Response code: ${response.code()}")
                        Log.d(TAG, "Response: ${response.raw().toString()}")
                        Log.d(TAG, "Response body: ${response.body()}")
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterActivity,"Gagal mendaftarkan akun", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}