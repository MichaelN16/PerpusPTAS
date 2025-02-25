package com.perkantas.perpusptas_new.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.perkantas.perpusptas_new.activity.PasswordChangeActivity
import com.perkantas.perpusptas_new.activity.ProfileEditActivity
import com.perkantas.perpusptas_new.model.MyProfileResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.util.dateFormatConverter
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

        //fetch user data from API
        fetchProfile()

        binding.updateBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileEditActivity::class.java))
        }

        binding.passwordTv.setOnClickListener {
            startActivity(Intent(requireContext(), PasswordChangeActivity::class.java))
        }

        binding.updateBtn.setOnClickListener {
            startActivity(Intent(activity, ProfileEditActivity::class.java))
        }
        return binding.root
    }

    override fun onResume() {
        //load data when fragment clicked
        fetchProfile()
        super.onResume()
    }

    private fun fetchProfile() {
        apiClient.getApiService(requireContext()).getUserProfile()
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    // Error fetching posts
                    Toast.makeText(requireContext(), "Tidak terhubung dengan internet", Toast.LENGTH_SHORT).show()
                    return
                }

                override fun onResponse(call: Call<MyProfileResponse>, response: Response<MyProfileResponse>) {
                    // Handle function to display profile
                    if (response.isSuccessful) {
                        val profile = response.body()!!.dataProf
                        //val userId = sessionManager.saveUserId(profile)
                        sessionManager.saveUserData(profile)
                        //convert date
                        val formattedDate = dateFormatConverter(profile.birthDate,"dd/MM/yyyy")
                        binding.nameTv.text = profile.name
                        binding.emailTv.text = profile.email
                        binding.addressTv.text = profile.address
                        binding.birthPlaceTv.text = profile.birthPlace
                        binding.componentTv.text = profile.component
                        binding.birthDateTv.text = formattedDate
                        binding.phoneTv.text = profile.phone
                    } else {

                    }
                }
            })
    }
}