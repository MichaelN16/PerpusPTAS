package com.perkantas.perpusptas_new

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.perkantas.perpusptas_new.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

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
            Toast.makeText(this, "Harap masukkan nama Anda", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show()
        } else if (cPassword.isEmpty()) {
            Toast.makeText(this, "Konfirmasi password", Toast.LENGTH_SHORT).show()
        } else if (password != cPassword){
            Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show()
        } else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {

    }

    private fun updateUserInfo() {

    }

}