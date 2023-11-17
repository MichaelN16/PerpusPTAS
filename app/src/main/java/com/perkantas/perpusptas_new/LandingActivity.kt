package com.perkantas.perpusptas_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.perkantas.perpusptas_new.Model.PostsResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.databinding.ActivityLandingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        //handle click login button
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //handle click skip button
        binding.skipBtn.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun fetchPosts(){

        //pass the token parameter
        apiClient.getApiService(this).fetchPosts()
            .enqueue(object : Callback<PostsResponse>{
                override fun onFailure(call: Call<PostsResponse>, t: Throwable) {

                }

                override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>
                ) {
                    TODO("Not yet implemented")
                }
            })
    }
}