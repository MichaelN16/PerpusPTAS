package com.perkantas.perpusptas_new.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.databinding.ActivityRentBinding

class RentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}