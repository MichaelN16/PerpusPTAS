package com.perkantas.perpusptas_new.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.perkantas.perpusptas_new.Adapter.LibraryPagerAdapter
import com.perkantas.perpusptas_new.Auth.SessionManager
import com.perkantas.perpusptas_new.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(requireContext())

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        // Set up ViewPager and TabLayout
        val adapter = LibraryPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Customize tab labels based on your category data
            tab.text = "Category $position"
        }.attach()

        return view
    }
}
