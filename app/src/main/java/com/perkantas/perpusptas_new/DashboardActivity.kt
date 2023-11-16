package com.perkantas.perpusptas_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.perkantas.perpusptas_new.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        checkUser()

        //setupWithViewPagerAdapter(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        //handle click, logout btn
        binding.logoutBtn.setOnClickListener {
            sessionManager.deleteAuthToken()
            sessionManager.clearUserData()

            startActivity(Intent(this, LandingActivity::class.java))
        }
        //handle click, profile btn
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun checkUser() {
        if(sessionManager.isLoggedIn()){
            val email = sessionManager.fetchAuthToken()
            Log.d("Session Manager", "Retreived email : $email")
            binding.subtitleTv.text = email

            binding.profileBtn.visibility = View.VISIBLE
            binding.logoutBtn.visibility = View.VISIBLE
        } else{
            binding.subtitleTv.text = "Belum masuk akun"
            binding.profileBtn.visibility = View.GONE
            binding.logoutBtn.visibility = View.GONE
        }
    }

    private fun setupWithViewPagerAdapter(viewPager: ViewPager) {

    }

}