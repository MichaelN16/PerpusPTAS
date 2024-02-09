package com.perkantas.perpusptas_new.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.perkantas.perpusptas_new.adapter.HistoryPagerAdapter
import com.perkantas.perpusptas_new.auth.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentHistoryBinding
import com.perkantas.perpusptas_new.model.RentStatus
import com.perkantas.perpusptas_new.retrofit.ApiClient

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        sessionManager = SessionManager(requireContext())
        apiClient = ApiClient()

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        // Create your list of rent statuses here
        val rentStatusList = listOf(
            RentStatus("pending", "Pending"),
            RentStatus("renting", "Renting"),
            RentStatus("overdue", "Overdue"),
            RentStatus("finish", "Finish")
        )

        // Pass the rentStatusList to the adapter
        val adapter = HistoryPagerAdapter(requireActivity(), rentStatusList)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = rentStatusList[position].value // Set the tab text to the rent status value
        }.attach()
    }
}