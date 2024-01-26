package com.perkantas.perpusptas_new.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click, submit btn
        binding.submitBtn.setOnClickListener {
            //send token ke laravel (dicari dulu)

            startActivity(Intent(this, LoginActivity::class.java))
        }

        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}