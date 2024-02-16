package com.perkantas.perpusptas_new.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    val TAG = "Notification Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root
        rvNotification = binding.notificationRv

        sessionManager = SessionManager(requireContext())
        apiClient = ApiClient()

        fetchNotification()

        return view
    }

    private var listNotification = ArrayList<NotificationResponse.Data>()

    private fun fetchNotification() {
        listNotification.clear()
        val token = "Bearer ${sessionManager.fetchAuthToken()}"
        apiClient.getApiService(requireContext()).getNotification(token).enqueue(object : Callback<NotificationResponse>{
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                val notificationResponse = response.body()!!

                if (response.isSuccessful){
                    listNotification = ArrayList(notificationResponse.data)
                    showNotification()
                    Log.d(TAG, notificationResponse.toString())
                } else{
                    Log.d(TAG, notificationResponse.toString())
                }
            }
        })
    }

    private fun showNotification() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvNotification.layoutManager = layoutManager

        adapterNotification = AdapterNotification(listNotification)
        rvNotification.adapter = adapterNotification

    }

}