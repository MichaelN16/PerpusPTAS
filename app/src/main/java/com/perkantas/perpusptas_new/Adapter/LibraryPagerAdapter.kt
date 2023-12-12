package com.perkantas.perpusptas_new.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.perkantas.perpusptas_new.Fragment.BookFragment

class LibraryPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        // Return the number of categories you want to display
        return 3  // Adjust this based on your actual number of categories
    }

    override fun createFragment(position: Int): Fragment {
        // Create and return a new instance of BookFragment for each category
        return BookFragment.newInstance("CategoryId_$position", "Category $position")
    }
}
