package com.perkantas.perpusptas_new.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityPasswordChangeBinding
import com.perkantas.perpusptas_new.model.PasswordChangeRequest
import com.perkantas.perpusptas_new.model.PasswordChangeResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordChangeBinding
    private lateinit var progressDialog:ProgressDialog
    private lateinit var sessionManager:SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        binding.changePassBtn.setOnClickListener {
            validateData()
        }
    }

    private var password = ""
    private var newPassword = ""
    private var cNewPassword = ""

    private fun validateData() {
        password = binding.passwordEt.text.toString().trim()
        newPassword = binding.newPasswordEt.text.toString().trim()
        cNewPassword = binding.cNewPasswordEt.text.toString().trim()

        if (password.isEmpty()) {
            Toast.makeText(this, "Masukkan password lama Anda", Toast.LENGTH_SHORT).show()
        } else if (newPassword.isEmpty()) {
            Toast.makeText(this, "Masukkan password baru Anda", Toast.LENGTH_SHORT).show()
        } else if (cNewPassword.isEmpty()){
            Toast.makeText(this, "Konfirmasi password baru Anda", Toast.LENGTH_SHORT).show()
        } else if (newPassword != cNewPassword) {
            Toast.makeText(this, "Password baru tidak sesuai", Toast.LENGTH_SHORT).show()
        } else {
            changePasswordRequest()
        }
    }


    private fun changePasswordRequest() {
        val email = sessionManager.fetchUserData()?.email ?: return
        val passChange = PasswordChangeRequest(email, password,newPassword,cNewPassword)

        progressDialog.setMessage("Password sedang diganti...")
        progressDialog.show()

        apiClient.getApiService(this).passwordChange(passChange).enqueue(object : Callback<PasswordChangeResponse>{
            override fun onFailure(call: Call<PasswordChangeResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PasswordChangeActivity,"Gagal menghubungkan karena ${t.message} ", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PasswordChangeResponse>, response: Response<PasswordChangeResponse>) {
                progressDialog.dismiss()
                val updateResponse = response.body()

                if (updateResponse?.status == true) {
                    Toast.makeText(this@PasswordChangeActivity, "Berhasil memperbarui password!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@PasswordChangeActivity, "Berhasil memperbarui password!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }
}