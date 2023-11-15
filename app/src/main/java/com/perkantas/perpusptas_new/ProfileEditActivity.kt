package com.perkantas.perpusptas_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle update profile
        binding.updateBtn.setOnClickListener {
            updateProfile()
        }

    }

    private fun updateProfile() {
        TODO("Not yet implemented")
    }
}