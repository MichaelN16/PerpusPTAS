package com.perkantas.perpusptas_new.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.perkantas.perpusptas_new.fragment.RentListFragment
import com.perkantas.perpusptas_new.model.RentStatus

class HistoryPagerAdapter(fragmentActivity: FragmentActivity, private val rentStatusList: List<RentStatus>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int { //arraylist count
        // Return the number of categories you want to display
        return rentStatusList.size  // Adjust this based on your actual number of categories
    }

    override fun createFragment(position: Int): Fragment {
        val rentStatus = rentStatusList[position]

        return RentListFragment.newInstance(rentStatus.key, rentStatus.value)
    }
}