package com.perkantas.perpusptas_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click, open edit profile
        binding.profileEditBtn.setOnClickListener {
            startActivity(Intent(this,ProfileEditActivity::class.java))
        }
    }
}