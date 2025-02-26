package com.perkantas.perpusptas_new.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perkantas.perpusptas_new.activity.NotificationDetailActivity
import com.perkantas.perpusptas_new.adapter.AdapterNotification
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentNotificationBinding
import com.perkantas.perpusptas_new.model.NotificationResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var rvNotification:RecyclerView
    private lateinit var adapterNotification: AdapterNotification

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root
        rvNotification = binding.notificationRv

        sessionManager = SessionManager(requireContext())
        apiClient = ApiClient()

        //progress bar
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        fetchNotification()

        return view
    }

    override fun onResume() {
        fetchNotification()
        super.onResume()
    }

    private var listNotification = ArrayList<NotificationResponse.Data>()

    private fun fetchNotification() {
        listNotification.clear()


        apiClient.getApiService(requireContext()).getNotification().enqueue(object : Callback<NotificationResponse>{
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Tidak terhubung dengan internet", Toast.LENGTH_SHORT).show()
                return
            }

            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                binding.progressBar.visibility = View.GONE
                val notificationResponse = response.body()!!

                if (response.isSuccessful){
                    listNotification = ArrayList(notificationResponse.data)
                    showNotification()
                } else{
                    Log.d("Notification Fragment", notificationResponse.toString())
                }
            }
        })
    }

    private fun showNotification() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvNotification.layoutManager = layoutManager

        if (listNotification.isEmpty()){
            rvNotification.visibility = View.GONE
            binding.noItemTv.visibility = View.VISIBLE
        } else {
            binding.noItemTv.visibility = View.GONE

            adapterNotification = AdapterNotification(listNotification, object : AdapterNotification.OnAdapterListener{
                override fun onClick(data: NotificationResponse.Data) {
                    val intent = Intent(requireContext(), NotificationDetailActivity::class.java)
                    intent.putExtra("dataNotification", data)
                    startActivity(intent)
                }
            })
            rvNotification.adapter = adapterNotification
        }

    }
}