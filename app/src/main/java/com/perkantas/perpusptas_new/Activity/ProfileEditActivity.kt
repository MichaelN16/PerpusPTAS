package com.perkantas.perpusptas_new.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.perkantas.perpusptas_new.R
import com.perkantas.perpusptas_new.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadArrayComponent()
        /*loadArrayGender()*/
        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle update profile
        binding.updateBtn.setOnClickListener {
            updateProfile()
            finish()
        }

    }

    /*private fun loadArrayGender() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, gender)
        binding.genderAc.setAdapter(arrayAdapter)
    }*/

    private fun loadArrayComponent() {
        val komponen = resources.getStringArray(R.array.komponen)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, komponen)
        binding.componentAc.setAdapter(arrayAdapter)
    }

    private fun updateProfile() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}