package com.perkantas.perpusptas_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click rent
        binding.rentBookBtn.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }
        //handle back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}