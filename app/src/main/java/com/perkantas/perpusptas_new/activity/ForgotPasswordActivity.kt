package com.perkantas.perpusptas_new.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityForgotPasswordBinding
import com.perkantas.perpusptas_new.model.PasswordResetResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        binding.resetPasswordBtn.setOnClickListener {
            validateData()
        }
    }

    private var email = ""

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Masukkan email yang telah terdaftar", Toast.LENGTH_SHORT).show()
        } else{
            passwordReset()
        }
    }

    private fun passwordReset() {
        progressDialog.setMessage("Mengajukan password reset...")
        progressDialog.show()

        apiClient.getApiService(this).passwordReset(email).enqueue(object : Callback<PasswordResetResponse>{
            override fun onFailure(call: Call<PasswordResetResponse>, t: Throwable) {
                progressDialog.dismiss()
                Log.e("Failure", "${t.message}")
                Toast.makeText(this@ForgotPasswordActivity, "Gagal terhubung ke server, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<PasswordResetResponse>, response: Response<PasswordResetResponse>) {
                progressDialog.dismiss()
                Toast.makeText(this@ForgotPasswordActivity, "Berhasil mengirim konfirmasi password, silahkan cek email Anda!", Toast.LENGTH_SHORT).show()
                finish()
            }

        })
    }

}