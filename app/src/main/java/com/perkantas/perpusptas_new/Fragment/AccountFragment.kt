package com.perkantas.perpusptas_new.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.perkantas.perpusptas_new.Activity.ForgotPasswordActivity
import com.perkantas.perpusptas_new.Activity.ProfileEditActivity
import com.perkantas.perpusptas_new.Model.MyProfileResponse
import com.perkantas.perpusptas_new.Retrofit.ApiClient
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentAccountBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)

        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())

        binding.updateBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileEditActivity::class.java))
        }

        binding.passwordTv.setOnClickListener {
            startActivity(Intent(requireContext(), ForgotPasswordActivity::class.java))
        }

        checkUser()

        binding.updateBtn.setOnClickListener {
            // Pass the profile data to ProfileEditActivity
            val intent = Intent(requireContext(), ProfileEditActivity::class.java)
            intent.putExtra("NAME", binding.nameTv.text.toString())
            intent.putExtra("ADDRESS", binding.addressTv.text.toString())
            intent.putExtra("BIRTH_PLACE", binding.birthPlaceTv.text.toString())
            intent.putExtra("COMPONENT", binding.componentTv.text.toString())
            startActivity(intent)
        }

        return binding.root
    }

    private fun checkUser() {
        if(sessionManager.isLoggedIn()){
            fetchProfile()
        } else {
            Log.d("Response :", "Pindah ke menu login")
        }
    }

    private fun fetchProfile() {
        // Pass the token as parameter
        apiClient.getApiService(requireContext()).getUserProfile(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    // Error fetching posts
                    Toast.makeText(requireContext(), "Gagal mengambil data karena ${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<MyProfileResponse>, response: Response<MyProfileResponse>) {
                    // Handle function to display posts
                    val profile = response.body()!!.dataProf
                    binding.nameTv.text = profile.name
                    binding.emailTv.text = profile.email
                    binding.addressTv.text = profile.address
                    binding.birthPlaceTv.text = profile.birthPlace
                    binding.componentTv.text = profile.component

                }
            })
    }

}