package com.perkantas.perpusptas_new.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.perkantas.perpusptas_new.adapter.AdapterNotification
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.ActivityNotificationDetailBinding
import com.perkantas.perpusptas_new.model.NotificationReadRequest
import com.perkantas.perpusptas_new.model.NotificationResponse
import com.perkantas.perpusptas_new.retrofit.ApiClient
import com.perkantas.perpusptas_new.util.getIntervalFromTimestamp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationDetailBinding
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var notificationData: NotificationResponse.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        notificationData = intent.getSerializableExtra("dataNotification") as NotificationResponse.Data

        binding.titleTv.text = notificationData.data.title
        binding.messageTv.text = notificationData.data.message
        binding.timestampTv.text = getIntervalFromTimestamp(notificationData.created_at)

        readMark()
    }

    private fun readMark() {
        val token = "Bearer ${sessionManager.fetchAuthToken()}"
        val idNotification = notificationData.id

        apiClient.getApiService(this).sendReadMark(token, idNotification).enqueue(object : Callback<NotificationResponse>{
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.e("Response", "Error because of ${t.message}", )
            }

            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                if (response.isSuccessful) {
                    Log.d("Response", "Success, read_at ${notificationData.read_at}")
                } else {
                    Log.e("Response", "Error" )
                }
            }
        })
    }
}