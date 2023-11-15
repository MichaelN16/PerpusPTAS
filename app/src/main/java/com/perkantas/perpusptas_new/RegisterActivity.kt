package com.perkantas.perpusptas_new

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

    private fun validateData() {
        //Input, gather all user information data
        val name = binding.nameEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()
        val cPassword = binding.cPasswordEt.text.toString().trim()

        //Validate Data, to prevent empty or unvalidate data
        if (name.isEmpty()) {
            binding.nameEt.error = "Harap masukkan nama Anda"
            binding.nameEt.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "Masukkan email yang valid"
            binding.emailEt.requestFocus()
            return
        } else if (password.isEmpty()) {
            binding.passwordEt.error = "Masukkan password"
            binding.passwordEt.requestFocus()
            return
        } else if (cPassword.isEmpty()) {
            binding.cPasswordEt.error = "Konfirmasi password"
            binding.cPasswordEt.requestFocus()
            return
        } else if (password != cPassword){
            binding.cPasswordEt.error = "Password tidak sesuai"
            binding.cPasswordEt.requestFocus()
            return
        } else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        apiClient.getApiService(this).register(RegisterRequest(name=name, email=email, password = password, cPassword = password))
            .enqueue(object : Callback<RegisterResponse>{
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity,"Gagal mendaftarkan karena ${t.message} ", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    val registerResponse = response.body()

                    if (registerResponse?.statusCode==true && registerResponse.dataReg.authToken !=null){
                        sessionManager.saveAuthToken(registerResponse.dataReg.authToken)
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                    else{
                        Log.d(TAG, "Response : " + response.body())
                        Toast.makeText(this@RegisterActivity,"Gagal mendaftarkan akun", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}