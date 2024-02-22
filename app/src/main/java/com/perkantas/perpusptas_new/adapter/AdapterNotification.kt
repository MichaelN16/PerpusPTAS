package com.perkantas.perpusptas_new.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.perkantas.perpusptas_new.databinding.ItemNotificationBinding
import com.perkantas.perpusptas_new.model.NotificationResponse
import com.perkantas.perpusptas_new.util.getIntervalFromTimestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AdapterNotification (var data : ArrayList<NotificationResponse.Data>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<AdapterNotification.Holder>() {

    class Holder (val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val notification = data[position]
        holder.binding.titleTv.text = notification.data.title
        holder.binding.messageTv.text = notification.data.message
        holder.binding.timestampTv.text = getIntervalFromTimestamp(notification.created_at)

        holder.itemView.setOnClickListener {
            listener.onClick(notification)
        }
    }

    override fun getItemCount():Int = data.size

     interface OnAdapterListener{
         fun onClick(data: NotificationResponse.Data)
     }

}